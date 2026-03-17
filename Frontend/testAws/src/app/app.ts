import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from './user.service';
import { BehaviorSubject } from 'rxjs';
import { switchMap, map, tap } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html'
})
export class App {
  name = '';

  private refreshUsers$ = new BehaviorSubject<void>(undefined);

  // Observables for async pipe
  localUsers$ = this.refreshUsers$.pipe(
    switchMap(() => this.userService.getUsers()),
    map((res: any) => res.local.map((u: any) => ({ id: u.id, name: u.name }))), // map to objects
    tap(users => console.log('Local users:', users))
  );

  awsUsers$ = this.refreshUsers$.pipe(
    switchMap(() => this.userService.getUsers()),
    map((res: any) => res.aws), // already {id, name} from backend
    tap(users => console.log('AWS users:', users))
  );

  constructor(private userService: UserService) {}

  addUser() {
    if (!this.name) return;

    const userName = this.name; // store before clearing

    this.userService.saveUser(this.name).subscribe({
      next: () => {
        alert(`User "${userName}" saved successfully!`); // popup
        this.name = '';
        this.refreshUsers$.next(); // trigger refresh
      },
      error: err => {
        console.error('Error saving user', err);
        alert('Failed to save user!');
      }
    });
  }
}


// import { Component } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { FormsModule } from '@angular/forms';
// import { UserService } from './user.service';
// import { BehaviorSubject } from 'rxjs';
// import { switchMap, tap } from 'rxjs/operators';

// @Component({
//   selector: 'app-root',
//   standalone: true,
//   imports: [CommonModule, FormsModule],
//   templateUrl: './app.html'
// })

// export class App {
//   name = '';

//   // Subjects to hold user lists
//   private refreshUsers$ = new BehaviorSubject<void>(undefined);
//   localUsers$ = this.refreshUsers$.pipe(
//     switchMap(() => this.userService.getUsers()),
//     // map to local users
//     tap(res => console.log('Local users:', res.local))
//   );
//   awsUsers$ = this.refreshUsers$.pipe(
//     switchMap(() => this.userService.getUsers()),
//     tap(res => console.log('AWS users:', res.aws))
//   );

//   constructor(private userService: UserService) {}

//   addUser() {
//     if (!this.name) return;

//     this.userService.saveUser(this.name).subscribe({
//       next: () => {
//         alert(`User "${this.name}" saved successfully!`); // popup notification
//         this.name = '';
//         this.refreshUsers$.next(); // trigger refresh
//       },
//       error: err => {
//         console.error('Error saving user', err);
//         alert('Failed to save user!');
//       }
//     });
//   }
// }


// // import { Component, OnInit } from '@angular/core';
// // import { CommonModule } from '@angular/common';
// // import { FormsModule } from '@angular/forms';
// // import { UserService } from './user.service';

// // @Component({
// //   selector: 'app-root',
// //   standalone: true,
// //   imports: [CommonModule, FormsModule], // 👈 VERY IMPORTANT
// //   templateUrl: './app.html'
// // })
// // export class App implements OnInit {

// //   name = '';
// //   localUsers: { id: number | string; name: string }[] = [];
// //   awsUsers: { id: number | string; name: string }[] = [];

// //   constructor(private userService: UserService) {}

// //   addUser() {
// //     if (!this.name) return;

// //     this.userService.saveUser(this.name).subscribe(() => {
// //       this.name = '';
// //       this.loadUsers();
// //     });
// //   }

// //   loadUsers() {
// //     this.userService.getUsers().subscribe((res: any) => {

// //       this.localUsers = res.local;
// //       this.awsUsers = res.aws;
// //     });
// //   }

// //   ngOnInit() {
// //     this.loadUsers();
// //   }
// // }
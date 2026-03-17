import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
  private baseUrl = 'https://iltyggd6f1.execute-api.us-east-1.amazonaws.com/users';

  constructor(private http: HttpClient) {}

  saveUser(name: string): Observable<any> {
    return this.http.post(this.baseUrl, { name });
  }

  getUsers(): Observable<{ local: string[], aws: any[] }> {
    return this.http.get<{ local: string[], aws: any[] }>(this.baseUrl);
  }
}

// import { HttpClient } from '@angular/common/http';
// import { Injectable } from '@angular/core';

// @Injectable({ providedIn: 'root' })
// export class UserService {

//   private baseUrl = 'http://localhost:8080/users';

//   constructor(private http: HttpClient) {}

//   saveUser(name: string) {
//     return this.http.post(this.baseUrl, { name });
//   }

//   getUsers() {
//     return this.http.get(this.baseUrl);
//   }
// }
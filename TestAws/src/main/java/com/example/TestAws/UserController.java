package com.example.TestAws;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final DynamoDBService dynamoDBService = new DynamoDBService();
    private final LocalDBService localDBService = new LocalDBService();

    @PostMapping
    public Map<String, String> addUser(@RequestBody UserRequest userRequest) throws SQLException {

        String id = UUID.randomUUID().toString();

        localDBService.saveUser(userRequest.getName());
        dynamoDBService.saveUser(id, userRequest.getName());

        Map<String, String> response = new HashMap<>();
        response.put("message", "User saved successfully");
        response.put("name", userRequest.getName());

        return response;
    }
//    @PostMapping
//    public String addUser(@RequestBody UserRequest userRequest) throws SQLException {
//        String id = UUID.randomUUID().toString();
//
//        // Save locally
//        localDBService.saveUser(userRequest.getName());
//
//        // Save in DynamoDB
//        dynamoDBService.saveUser(id, userRequest.getName());
//
//        return "User saved: " + userRequest.getName();
//    }

    @GetMapping
    public Map<String, Object> getUsers() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("local", localDBService.fetchUsers());

        try {
            response.put("aws", dynamoDBService.fetchUsers());
        } catch (Exception e) {
            e.printStackTrace();
            response.put("aws", e.getMessage());
        }

        return response;
    }

//    @GetMapping
//    public Object getUsers() throws SQLException {
//        List<String> localUsers = localDBService.fetchUsers();
//        Object awsUsers = dynamoDBService.fetchUsers();
//
//        return new Object() {
//            public List<String> local = localUsers;
//            public Object aws = awsUsers;
//        };
//    }

    static class UserRequest {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}

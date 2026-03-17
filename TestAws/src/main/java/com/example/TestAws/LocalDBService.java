package com.example.TestAws;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalDBService {

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:local.db");
    }

    // 🔥 Always ensure table exists
    private void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL" +
                ")";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void saveUser(String name) throws SQLException {
        createTableIfNotExists();  // 👈 important

        String sql = "INSERT INTO users(name) VALUES(?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public List<Map<String, Object>> fetchUsers() throws SQLException {
        createTableIfNotExists();

        List<Map<String, Object>> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, Object> user = new HashMap<>();
                user.put("id", rs.getInt("id"));
                user.put("name", rs.getString("name"));
                users.add(user);
            }
        }
        return users;
    }

//    public List<String> fetchUsers() throws SQLException {
//        createTableIfNotExists();  // 👈 important
//
//        List<String> users = new ArrayList<>();
//        String sql = "SELECT * FROM users";
//
//        try (Connection conn = connect();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                users.add(rs.getInt("id") + ": " + rs.getString("name"));
//            }
//        }
//        return users;
//    }
}
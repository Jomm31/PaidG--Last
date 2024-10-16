package com.example.paidg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database { // Updated to use uppercase "D"

    private static final String URL = "jdbc:mysql://localhost:3306/gamehub";
    private static final String USER = "root"; // your database username
    private static final String PASSWORD = ""; // your database password

    public static Connection connectDb() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }
}

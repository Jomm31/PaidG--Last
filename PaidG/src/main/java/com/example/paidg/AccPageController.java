package com.example.paidg;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccPageController {



    @FXML
    private TextField SignInemailField;
    @FXML
    private PasswordField SignInpassword;
    @FXML
    private Button signInButton;

    public static int user_id;
    @FXML
    protected void onSignUpButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent registerPane = fxmlLoader.load();
            Stage stage = (Stage) signInButton.getScene().getWindow();
            Scene scene = new Scene(registerPane);
            stage.setScene(scene);
            stage.setTitle("Register"); // Set the title of the new scene
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load registration page.");
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLoginButtonClick() {
        String email = SignInemailField.getText();
        String password = SignInpassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Email and password must be filled out.");
            return;
        }

        if (authenticateUser(email, password)) {
            openGamesPage();
        } else {
            showAlert("Error", "Invalid email or password.");
        }
    }

    private boolean authenticateUser(String email, String password) {
        Connection connection = Database.connectDb();
        if (connection == null) {
            showAlert("Error", "Failed to connect to the database.");
            return false;
        }

        // Modified SQL query to include UserID
        String sql = "SELECT UserID, Password FROM user WHERE Email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                user_id = rs.getInt("UserID"); // Corrected user ID retrieval
                System.out.println("Attempting to authenticate:");
                System.out.println("Email: " + email);
                System.out.println("Stored Password: " + storedPassword);

                // Assuming you have hashed passwords in your database
                return verifyPassword(password, storedPassword);
            } else {
                // No user found
                System.out.println("No user found with email: " + email);
                return false;
            }
        } catch (SQLException e) {
            showAlert("Error", "Database error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean verifyPassword(String inputPassword, String storedHashedPassword) {
        // Implement your hashing verification logic here
        // For example, using BCrypt:
        // return BCrypt.checkpw(inputPassword, storedHashedPassword);
        return inputPassword.equals(storedHashedPassword); // Remove this after hashing implementation
    }

    private void openGamesPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Games.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) signInButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Games"); // Set the title of the new scene
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load games page.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

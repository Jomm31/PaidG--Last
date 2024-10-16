package com.example.paidg;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GameController {

    @FXML
    private Button GamesBought;

    @FXML
    private Button Logout;

    @FXML
    private Button buyGodOfWar;

    @FXML
    private Button buyGTAV;

    @FXML
    private Button buyStickman;

    @FXML
    private Button buyMinecraft;

    @FXML
    private Button buyRiseOfKingdoms;

    @FXML
    private Button buyTheSleepingGirl;

    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    @FXML
    public void initialize() {



        GamesBought.setOnAction(event -> openGamesBought());
        // Initialize buttons and set actions
        Logout.setOnAction(event -> handleLogout());

        // Add actions for buy buttons as needed
        buyGodOfWar.setOnAction(event -> {
            boolean h = false;
            if(!h){
                BuyGodofwarr();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("successfully purchased");
                alert.showAndWait();
                h = true;
            } else {
                buySG();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Already purchased");
                alert.setContentText("Already purchased");
                alert.showAndWait();
            }
        });
        // Repeat for other buttons...
        buyGTAV.setOnAction(event ->  {
            boolean h = false;
            if(!h){
                BuyGTAVV();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("successfully purchased");
                alert.showAndWait();
                h = true;
            } else {
                buySG();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Already purchased");
                alert.setContentText("Already purchased");
                alert.showAndWait();
            }
        });
        buyStickman.setOnAction(event -> {;
            boolean h = false;
            if(!h){
                buyStickman();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("successfully purchased");
                alert.showAndWait();
                h = true;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Already purchased");
                alert.setContentText("Already purchased");
                alert.showAndWait();
            }});
        buyMinecraft.setOnAction(event ->  {

            boolean h = false;
            if(!h){
                buyminecar();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("successfully purchased");
                alert.showAndWait();
                h = true;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Already purchased");
                alert.setContentText("Already purchased");
                alert.showAndWait();
            }
        });
        buyRiseOfKingdoms.setOnAction(event -> {

            boolean h = false;
            if(!h){
                buyRoK();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("successfully purchased");
                alert.showAndWait();
                h = true;
            } else {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Already purchased");
                alert.setContentText("Already purchased");
                alert.showAndWait();
            }
        });
        buyTheSleepingGirl.setOnAction(event ->  {
            boolean h = false;
            if(!h){
                buySG();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("successfully purchased");
                alert.showAndWait();
                h = true;
            } else {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Already purchased");
                alert.setContentText("Already purchased");
                alert.showAndWait();
            }


        });

    }
    public void buySG(){

        executeTransaction(4);
    }
    public void buyRoK(){
        executeTransaction(3);
    }
    public void buyminecar(){
        executeTransaction(2);
    }
    public void buyStickman(){
        executeTransaction(1);
    }
    public void BuyGTAVV(){
        executeTransaction(6);
    }
    public void BuyGodofwarr(){
        executeTransaction(5);
    }

    private void executeTransaction(int gameId) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Database.connectDb();
            if (con != null) {
                String sql = "INSERT INTO transaction (Date, GameID, UserID) VALUES (?, ?, ?)";
                ps = con.prepareStatement(sql);

                ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));  // Set the formatted date
                ps.setInt(2, gameId);                          // Set the game ID
                ps.setInt(3, AccPageController.user_id);      // Set the user ID

                // Execute the update
                ps.executeUpdate();

                // Optionally: Show success message or handle further logic here
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace(); // Log the exception for debugging
            throw new RuntimeException("Error occurred while processing the transaction: " + e.getMessage());
        } finally {
            // Close resources in the finally block to ensure they're always closed
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Log any exceptions that occur during closing
            }
        }
    }

    private void openGamesBought() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GamesBought.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) GamesBought.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLogout() {
        try {
            // Load the AccPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AccPage.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) Logout.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Account Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

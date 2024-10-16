package com.example.paidg;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class GamesBoughtController {

    @FXML
    private Button GamesBought;
    @FXML
    private TableColumn<Transaction, Integer> Table_T_Transaction;

    @FXML
    private TableColumn<Transaction, Date> Table_T_col_Date;

    @FXML
    private TableColumn<Transaction, String> Table_T_col_Game_title;

    @FXML
    private TableColumn<Transaction, Integer> Table_T_col_game_id;

    @FXML
    private TableColumn<Transaction, Integer> Table_T_col_userid;

    @FXML
    private TableView<Transaction> Table_Transaction;

    @FXML
    public void initialize() {

        Table_T_Transaction.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        Table_T_col_Date.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        Table_T_col_Game_title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Table_T_col_game_id.setCellValueFactory(new PropertyValueFactory<>("gameId"));
        Table_T_col_userid.setCellValueFactory(new PropertyValueFactory<>("userId"));

        ObservableList<Transaction> transactions = getTransactionData();
        Table_Transaction.setItems(transactions);

        GamesBought.setOnAction(event -> goBack());
    }
    private ObservableList<Transaction> getTransactionData() {

        ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM transaction WHERE UserID = ?";
        try (Connection con = Database.connectDb()) {
            assert con != null;
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, AccPageController.user_id); // Use prepared statement for parameter
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int transactionId = resultSet.getInt("transactionID"); // Adjust column name as necessary
                    Date transactionDate = resultSet.getDate("Date");
                    int gameId = resultSet.getInt("GameId");
                    int userId = resultSet.getInt("UserID");
                    String gameTitle = getGameTitle(gameId);
                    // Create and add the transaction object
                    Transaction transaction = new Transaction(transactionId, transactionDate, gameTitle, gameId, userId);
                    transactionList.add(transaction);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactionList;
    }

    private String getGameTitle(int gameId) {
        String gameTitle = "";
        String sql = "SELECT Title FROM games WHERE GameID = ?";
        try (Connection con = Database.connectDb()) {
            assert con != null;
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, gameId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    gameTitle = resultSet.getString("Title");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameTitle;
    }


    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("games.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) GamesBought.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

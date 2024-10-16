package com.example.paidg;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Transaction {
    private int transactionId;
    private Date transactionDate;
    private String Title;
    private int gameId;
    private int userId;

    public Transaction(int transactionId, Date transactionDate,String Title, int gameId, int userId) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.Title = Title;
        this.gameId = gameId;
        this.userId = userId;
    }

    // Getters
    public int getTransactionId() {
        return transactionId;
    }
    public String getTitle(){
        return Title;
    }
    public Date getTransactionDate() {
        return transactionDate;
    }
    public int getGameId() {
        return gameId;
    }
    public int getUserId() {
        return userId;
    }
}
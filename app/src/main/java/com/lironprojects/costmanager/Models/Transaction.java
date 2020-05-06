package com.lironprojects.costmanager.Models;

import java.util.Date;

public class Transaction {
    private int id;
    private int transactionID;
    private Date date;
    private int amount;
    private String name;
    private double price;
    private boolean repeat;
    private boolean income;
    private String category;
    private String currency;
    private String description;
    private String paymentType;

    public Transaction(){}

    public Transaction(int id, int transactionID, Date date, int amount, String name, double price, boolean isRepeat,
                boolean isIncome, String category, String currency, String description, String paymentType){
        setId(id);
        setTransactionID(transactionID);
        setDate(date);
        setAmount(amount);
        setName(name);
        setPrice(price);
        setRepeat(isRepeat);
        setIncome(isIncome);
        setCategory(category);
        setCurrency(currency);
        setDescription(description);
        setPaymentType(paymentType);
    }

    public int getId() {
        return id;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public Date getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getPaymentType() {
        return paymentType;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    private void setAmount(int amount) {
        this.amount = amount;
    }

    private void setCategory(String category) {
        this.category = category;
    }

    private void setCurrency(String currency) {
        this.currency = currency;
    }

    private void setDate(Date date) {
        this.date = date;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    private void setPrice(double price) {
        this.price = price;
    }

    private void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isRepeat() {
        return repeat;
    }

    private void setIncome(boolean income) {
        this.income = income;
    }

    public boolean isIncome() {
        return income;
    }
}

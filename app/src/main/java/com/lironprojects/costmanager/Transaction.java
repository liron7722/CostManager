package com.lironprojects.costmanager;

import java.util.Date;

public class Transaction {
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
    private String OLC;

    Transaction(Date date, int amount, String name, double price, boolean isRepeat, boolean isIncome, String category, String currency, String description, String paymentType){
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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public boolean isIncome() {
        return income;
    }
}

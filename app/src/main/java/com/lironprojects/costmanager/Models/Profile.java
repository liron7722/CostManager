package com.lironprojects.costmanager.Models;

public class Profile {
    private int id;
    private String name;
    private String email;
    private double weeklyBudget;
    private String incomeColor;
    private String expensesColor;

    public Profile(int id, String name, String email, double weeklyBudget, String incomeColor, String expensesColor){
        updateProfile(id, name, email, weeklyBudget, incomeColor, expensesColor);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getWeeklyBudget() {
        return weeklyBudget;
    }

    public String getExpensesColor() {
        return expensesColor;
    }

    public String getIncomeColor() {
        return incomeColor;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setWeeklyBudget(double weeklyBudget) {
        this.weeklyBudget = weeklyBudget;
    }

    private void setIncomeColor(String incomeColor) {
        this.incomeColor = incomeColor;
    }

    private void setExpensesColor(String expensesColor) {
        this.expensesColor = expensesColor;
    }

    protected void updateProfile(int id, String name, String email, double weeklyBudget, String incomeColor, String expensesColor){
        setId(id);
        setName(name);
        setEmail(email);
        setWeeklyBudget(weeklyBudget);
        setIncomeColor(incomeColor);
        setExpensesColor(expensesColor);
    }
}

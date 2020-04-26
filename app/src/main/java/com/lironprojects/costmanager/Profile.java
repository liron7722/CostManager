package com.lironprojects.costmanager;

import android.provider.ContactsContract;

public class Profile {
    private int id;
    private String name;
    private String email;
    private double weeklyBudget;
    private String incomeColor;
    private String expensesColor;

    Profile(int id, String name, String email, double weeklyBudget, String incomeColor, String expensesColor){
        setId(id);
        setName(name);
        setEmail(email);
        setWeeklyBudget(weeklyBudget);
        setIncomeColor(incomeColor);
        setExpensesColor(expensesColor);
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWeeklyBudget(double weeklyBudget) {
        this.weeklyBudget = weeklyBudget;
    }

    public void setIncomeColor(String incomeColor) {
        this.incomeColor = incomeColor;
    }

    public void setExpensesColor(String expensesColor) {
        this.expensesColor = expensesColor;
    }
}

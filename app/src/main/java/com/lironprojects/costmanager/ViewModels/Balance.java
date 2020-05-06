 package com.lironprojects.costmanager.ViewModels;

import android.database.Cursor;

import com.lironprojects.costmanager.DB.CostManagerDB;
import com.lironprojects.costmanager.Models.Profile;
import com.lironprojects.costmanager.Models.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Balance {
    private List<Transaction> transactions = new ArrayList<>();
    private Profile profile;

    Balance(int id, CostManagerDB db){
        setProfile(id, db);
        setTransactions(id, db);
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public Profile getProfile() {
        return this.profile;
    }

    private void setProfile(int key, CostManagerDB db){
        String idName = "id";
        Cursor cursor = db.query("profile", "id", null);
        JSONObject data =  db.getDataFromProfileTable(cursor);
        try {
            int id = (int) data.get("id");
            String name = (String) data.get("name");
            String email = (String) data.get("email");
            double weeklyBudget = (double) data.get("weeklyBudget");
            String incomeColor = (String) data.get("incomeColor");
            String expensesColor = (String) data.get("expensesColor");
            this.profile = new Profile(id, name, email, weeklyBudget, incomeColor, expensesColor);
        } catch (JSONException e){
            System.out.println("Error\n" + e.getMessage());
        }
    }

    private void setTransactions(int key, CostManagerDB db){
        String idName = "transactionID";
        Cursor cursor = db.query("profile", "id", null);
        JSONArray data = db.getDataFromTransactionsTable(cursor);
        for(int i = 0; i < data.length(); i++){
            try {
                int id = (int) data.getJSONObject(i).get("id");
                int transactionID = (int) data.getJSONObject(i).get("transactionID");
                Date date = (Date) data.getJSONObject(i).get("date");
                int amount = (int) data.getJSONObject(i).get("amount");
                String name = (String) data.getJSONObject(i).get("name");
                double price = (double) data.getJSONObject(i).get("price");
                boolean isRepeat = (boolean) data.getJSONObject(i).get("isRepeat");
                boolean isIncome = (boolean) data.getJSONObject(i).get("isIncome");
                String category = (String) data.getJSONObject(i).get("category");
                String currency = (String) data.getJSONObject(i).get("currency");
                String description = (String) data.getJSONObject(i).get("description");
                String paymentType = (String) data.getJSONObject(i).get("paymentType");
                this.transactions.add(new Transaction(id, transactionID, date, amount, name, price, isRepeat, isIncome, category, currency, description, paymentType));
            } catch (JSONException e) {
                System.out.println("Error\n" + e.getMessage());
            }
        }
    }
}

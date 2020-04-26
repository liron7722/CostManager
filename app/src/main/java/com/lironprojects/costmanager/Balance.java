 package com.lironprojects.costmanager;

import java.util.Date;
import java.util.List;

public class Balance {
    private List<Transaction> transactions;
    private Profile profile;
    private SQLiteDB db;

    Balance(int id, SQLiteDB db){
        this.db = db;
        setProfile(id);
        setTransactions(id);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Profile getProfile() {
        return profile;
    }

    private void setTransactions(int id){
        Date date = new Date();
        Transaction a = new Transaction(date, 1, "store", 25.30, false, false, "food", "ILS", "milk and bread", "cash");
        Transaction b = new Transaction(date, 1, "pet store", 12.80, false, false, "food", "ILS", "milk and bread", "cash");
        Transaction c = new Transaction(date, 1, "salary", 50.10, true, true, "salary", "ILS", "job 1 salary", "bank transfer");

        addTransaction(a);
        addTransaction(b);
        addTransaction(c);
    }
    private void setProfile(int id){
        profile = new Profile(id,"name","email",1000.00,"incomeColor","expensesColor");
    }


    public void addTransaction(Transaction t){
        transactions.add(t);
    }
}

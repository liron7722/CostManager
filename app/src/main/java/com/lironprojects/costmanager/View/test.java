package com.lironprojects.costmanager.View;

import android.content.Context;
import android.widget.Toast;

import com.lironprojects.costmanager.DB.CostManagerDB;
import com.lironprojects.costmanager.Models.Profile;
import com.lironprojects.costmanager.Models.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test {

    private Profile profile;
    private List<Transaction> transactions = new ArrayList<>();

    public test() {
        //createDB();
        //Balance ba = new Balance(1, db);
        /*
        createProfile();
        printProfile();
        createTransaction();
        printTransactions();
        */
    }
    private void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private void createDB(){
        CostManagerDB db = new CostManagerDB(null);

    }

    private void createProfile(){
        this.profile = new Profile(1, "name", "email", 1000.00, "incomeColor", "expensesColor");
    }

    private void printProfile(){
        System.out.println("id\tname\temail\tweeklybudjet\tincomeColor\texpensesColor");
        System.out.println(this.profile.getId() + "\t" + this.profile.getName() + "\t" + this.profile.getEmail() + "\t" + this.profile.getWeeklyBudget() + "\t" +
                this.profile.getIncomeColor() + "\t" + this.profile.getExpensesColor());
    }

    private void createTransaction(){
        Date date = new Date();
        transactions.add(new Transaction(1, 0, date, 1, "store", 25.30, false, false, "food", "ILS", "milk and bread", "cash"));
        transactions.add(new Transaction(2, 1, date, 1, "pet store", 12.80, false, false, "food", "ILS", "milk and bread", "cash"));
        transactions.add(new Transaction(3, 2, date, 1, "salary", 50.10, true, true, "salary", "ILS", "job 1 salary", "bank transfer"));

    }

    private void printTransactions(){
        System.out.println("id\ttransactionID\tdate\tamount\tname\tprice\tisRepeat\tisIncome\tcategory\tcurrency\tdescription\tpaymentType");
        for (int i = 0; i < transactions.size(); i++){
            printTransaction(transactions.get(i));
        }
    }

    private void printTransaction(Transaction t){
        System.out.println(t.getId() + "\t" + t.getTransactionID() + "\t" + t.getDate() + "\t" + t.getAmount() + "\t" + t.getName() + "\t" + t.getPrice() + "\t" + t.isRepeat() + "\t" +
                t.isIncome() + "\t" + t.getCategory() + "\t" + t.getCurrency() + "\t" + t.getDescription()  + "\t" + t.getPaymentType());
    }
}

package com.lironprojects.costmanager.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteBasic extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = Names.DATABASE_NAME;    // Database Name
    private static final int DATABASE_Version = 1;    // Database Version

    SQLiteBasic(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            this.createProfileTable(db);
            this.createSettingsTable(db);
            this.createTransactionTable(db);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            System.out.println("OnUpgrade");
            dropTable(db);
            onCreate(db);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void dropTable(SQLiteDatabase db){
        for (String table_name : Names.Table_Names) {
            db.execSQL("DROP TABLE IF EXISTS " + table_name);
        }
    }

    private void createProfileTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + Names.Profile_Table + " ("
                + Names.UID + " integer PRIMARY KEY AUTOINCREMENT"
                + ", " + Names.Password + " text NOT NULL"
                + ", " + Names.Name + " text NOT NULL"
                + ", " + Names.Email + " text NOT NULL"
                + ");";
        db.execSQL(sql);
    }

    private void createSettingsTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + Names.Settings_Table + " ("
                + Names.UID + " integer REFERENCES " + Names.Profile_Table + "("+ Names.UID +")"
                + ", " + Names.WeeklyBudget + " text NOT NULL"
                + ", " + Names.IncomeColor + " text NOT NULL"
                + ", " + Names.ExpensesColor + " text NOT NULL"
                + ");";
        db.execSQL(sql);
    }

    private void createTransactionTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + Names.Transactions_Table + " ("
                + Names.UID + " integer NOT NULL" //FOREIGN KEY REFERENCES profile(id)"
                + ", " + Names.TID + " integer PRIMARY KEY AUTOINCREMENT"
                + ", " + Names.Date + " text NOT NULL"
                + ", " + Names.Amount + " integer NOT NULL"
                + ", " + Names.TName + " text NOT NULL"
                + ", " + Names.Price + " double NOT NULL"
                + ", " + Names.isRepeat + " real NOT NULL"
                + ", " + Names.isIncome + " real NOT NULL"
                + ", " + Names.Category + " text"
                + ", " + Names.Currency + " text"
                + ", " + Names.Description + " text"
                + ", " + Names.PaymentType + " text"
                + ");";
        db.execSQL(sql);
    }
}

package com.lironprojects.costmanager.DB;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteBasic extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CostManager.db";    // Database Name
    private static final int DATABASE_Version = 1;    // Database Version

    SQLiteBasic(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try {
            Log.i(Names.logTAG, "SQL DB onCreate");
            this.createProfileTable(db);
            this.createTransactionTable(db);
            Log.i(Names.logTAG, "SQL DB onCreate Finished");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Log.i(Names.logTAG, "SQL DB onUpgrade Finished");
            dropTable(db);
            onCreate(db);
            Log.i(Names.logTAG, "SQL DB onUpgrade Finished");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void dropTable(SQLiteDatabase db){
        for (String table_name : Names.Table_Names) {
            db.execSQL("DROP TABLE IF EXISTS " + table_name);
            Log.i(Names.logTAG, "SQL DB " + table_name + " dropped");
        }
    }

    private void createProfileTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + Names.Profile_Table + " ("
                + Names.UID + " integer PRIMARY KEY AUTOINCREMENT"
                + ", " + Names.Name + " text NOT NULL"
                + ", " + Names.Email + " text NOT NULL Unique"
                + ", " + Names.Password + " text NOT NULL"
                + ");";
        db.execSQL(sql);
        Log.i(Names.logTAG, "SQL DB " + Names.Profile_Table + " created");
    }

    private void createTransactionTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + Names.Transactions_Table + " ("
                + Names.UID + " integer NOT NULL" //FOREIGN KEY REFERENCES profile(id)"
                + ", " + Names.TID + " integer PRIMARY KEY AUTOINCREMENT"
                + ", " + Names.Date + " text NOT NULL"
                + ", " + Names.Amount + " integer NOT NULL"
                + ", " + Names.TName + " text NOT NULL"
                + ", " + Names.Price + " double NOT NULL"
                + ", " + Names.isIncome + " real NOT NULL"
                + ", " + Names.Category + " text NOT NULL"
                + ", " + Names.Currency + " text NOT NULL"
                + ", " + Names.Description + " text NOT NULL"
                + ", " + Names.PaymentType + " text NOT NULL"
                + ");";
        db.execSQL(sql);
        Log.i(Names.logTAG, "SQL DB " + Names.Transactions_Table + " created");
    }
}

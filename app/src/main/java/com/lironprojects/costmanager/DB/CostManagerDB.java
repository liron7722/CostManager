package com.lironprojects.costmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CostManagerDB{
    private SQLiteBasic DBHelper;

    public CostManagerDB(Context context){
        this.DBHelper = new SQLiteBasic(context);
    }

    public long insertToProfileTable(String name, String password, String email) {
        ContentValues values = new ContentValues();
        values.put(Names.Name, name);
        values.put(Names.Password, password);
        values.put(Names.Email, email);
        long id = this.DBHelper.getWritableDatabase().insert(Names.Profile_Table, null, values);
        this.DBHelper.close();
        return id;
    }

    public long insertToTransactionTable(int id, String date, int amount, String name, double price, boolean isIncome,
                                         String category, String currency, String description, String paymentType) {
        ContentValues values = new ContentValues();
        values.put(Names.UID, id);
        values.put(Names.Date, date);
        values.put(Names.Amount, amount);
        values.put(Names.TName, name);
        values.put(Names.Price, price);
        values.put(Names.isIncome, isIncome);
        values.put(Names.Category, category);
        values.put(Names.Currency, currency);
        values.put(Names.Description, description);
        values.put(Names.PaymentType, paymentType);
        long transactionID = this.DBHelper.getWritableDatabase().insert(Names.Transactions_Table, null, values);
        this.DBHelper.close();
        return transactionID;
    }

    @NonNull
    public Cursor query(String TABLE_NAME, String whereClause, String[] whereArgs){
        Cursor cursor = this.DBHelper.getWritableDatabase().query(
                TABLE_NAME,     // The table to query
                null,  // The array of columns to return (pass null to get all)
                whereClause,      // The columns for the WHERE clause
                whereArgs,  // The values for the WHERE clause
                null,  // don't group the rows
                null,   // don't filter by row groups
                null   // The sort order
        );
        cursor.close();
        return cursor;
    }

    public int update(String TABLE_NAME, ContentValues values, String whereClause, String[] whereArgs){
        return this.DBHelper.getWritableDatabase().update(TABLE_NAME, values, whereClause, whereArgs);
    }

    public int delete(String TABLE_NAME, String whereClause, String[] whereArgs){
        return this.DBHelper.getWritableDatabase().delete(TABLE_NAME,whereClause, whereArgs);
    }

    public JSONObject getDataFromProfileTable(@NonNull Cursor cursor){
        JSONObject jo = new JSONObject();
        try {
                jo.put(Names.UID, cursor.getInt(1));
                jo.put(Names.Name, cursor.getString(2));
                jo.put(Names.Password, cursor.getInt(3));
                jo.put(Names.Email, cursor.getString(4));
            } catch (JSONException e){
            System.out.println("Error\n" + e.getMessage());
        }
        return jo;
    }

    public JSONArray getDataFromTransactionsTable(@NonNull Cursor cursor) {
        JSONArray ja = new JSONArray();
        while (cursor.moveToNext()){
            JSONObject jo = new JSONObject();
            try {
                jo.put(Names.UID, cursor.getInt(1));
                jo.put(Names.TID, cursor.getInt(2));
                jo.put(Names.Date, cursor.getString(3));
                jo.put(Names.Amount, cursor.getInt(4));
                jo.put(Names.TName, cursor.getString(5));
                jo.put(Names.Price, cursor.getDouble(6));
                jo.put(Names.isIncome, cursor.getString(7));
                jo.put(Names.Category, cursor.getString(8));
                jo.put(Names.Currency, cursor.getString(9));
                jo.put(Names.Description, cursor.getString(10));
                jo.put(Names.PaymentType, cursor.getString(11));
            } catch (JSONException e) {
                System.out.println("Error\n" + e.getMessage());
            }
            ja.put(jo);
        }
        return ja;
    }

}
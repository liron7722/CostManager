package com.lironprojects.costmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

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
        long id = this.DBHelper.getWritableDatabase().insert(Names.Profile_Table,
                                                            null, values);
        //this.DBHelper.close();
        return id;
    }

    public void insertToTransactionTable(int id, String date, int amount, String name, double price,
                                         boolean isIncome, String category, String currency,
                                         String description, String paymentType) {
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
        this.DBHelper.getWritableDatabase().insert(Names.Transactions_Table, null, values);
        //this.DBHelper.close();
    }

    public Cursor query(String TABLE_NAME, String[] columns, String whereClause, String[] whereArgs){
        System.out.println("query values:");
        System.out.println("table: " + TABLE_NAME + ", columns: " + Arrays.toString(columns) + ", whereClause: " + whereClause + ", whereArgs: " + Arrays.toString(whereArgs));
        return this.DBHelper.getWritableDatabase().query(
                TABLE_NAME,     // The table to query
                columns,  // The array of columns to return (pass null to get all)
                null,      // The columns for the WHERE clause
                null,//whereArgs,  // The values for the WHERE clause
                null,  // don't group the rows
                null,   // don't filter by row groups
                null   // The sort order
        );
    }

    public int update(String TABLE_NAME, ContentValues values, String whereClause, String[] whereArgs){
        //values = {key1: "", key2: "", key3: ""}, whereClause = Names.UID + " = ?", whereArgs = { num as string}
        //values = {key1: "", key2: "", key3: ""}, whereClause = Names.TID + " = ?", whereArgs = { num as string}
        return this.DBHelper.getWritableDatabase().update(TABLE_NAME, values, whereClause, whereArgs);
    }

    public int delete(String TABLE_NAME, String whereClause, String[] whereArgs){
        //whereClause = Names.UID + " = ?", whereArgs = { num as string}
        //whereClause = Names.TID + " = ?", whereArgs = { num as string}
        return this.DBHelper.getWritableDatabase().delete(TABLE_NAME,whereClause, whereArgs);
    }

    public int getDataFromProfileTable(Cursor cursor){
        return cursor.getInt(cursor.getColumnIndex(Names.UID));
    }

    public JSONObject getDataFromTransactionsTable(Cursor cursor) {
        JSONObject result = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            while (cursor.moveToNext()){
                JSONObject jo = new JSONObject();
                jo.put(Names.TID, cursor.getInt(cursor.getColumnIndex(Names.TID)));
                jo.put(Names.Date, cursor.getString(cursor.getColumnIndex(Names.Date)));
                jo.put(Names.Category, cursor.getString(cursor.getColumnIndex(Names.Category)));
                jo.put(Names.TName, cursor.getString(cursor.getColumnIndex(Names.TName)));
                jo.put(Names.Amount, cursor.getInt(cursor.getColumnIndex(Names.Amount)));
                jo.put(Names.Price, cursor.getDouble(cursor.getColumnIndex(Names.Price)));
                jo.put(Names.Currency, cursor.getString(cursor.getColumnIndex(Names.Currency)));
                jo.put(Names.Description, cursor.getString(cursor.getColumnIndex(Names.Description)));
                jo.put(Names.isIncome, cursor.getString(cursor.getColumnIndex(Names.isIncome)));
                jo.put(Names.PaymentType, cursor.getString(cursor.getColumnIndex(Names.PaymentType)));
                ja.put(jo);
            }
            cursor.close();
            result.put("array", ja);
            } catch (/*JSONException |*/ Exception e) {
                System.out.println("Error in cursor\n" + e.getMessage());
        }
        return result;
    }
}
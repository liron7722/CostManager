package com.lironprojects.costmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;


import com.lironprojects.costmanager.Models.CostManagerException;

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
        Log.i(Names.logTAG, "Inside insertToProfileTable function");
        ContentValues values = new ContentValues();
        values.put(Names.Name, name);
        values.put(Names.Password, password);
        values.put(Names.Email, email);
        return this.DBHelper.getWritableDatabase().insert(Names.Profile_Table,
                                                            null, values);
    }

    public void insertToTransactionTable(int id, String date, int amount, String name, double price,
                                         boolean isIncome, String category, String currency,
                                         String description, String paymentType) {
        Log.i(Names.logTAG, "Inside insertToTransactionTable function");
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
    }

    public Cursor query(String TABLE_NAME, String[] columns, String whereClause, String[] whereArgs){
        Log.i(Names.logTAG, "Inside query function, query values: table: " + TABLE_NAME + ", columns: " +
                Arrays.toString(columns) + ", whereClause: " + whereClause + ", whereArgs: " + Arrays.toString(whereArgs));
        return this.DBHelper.getWritableDatabase().query(
                TABLE_NAME,     // The table to query
                columns,        // The array of columns to return (pass null to get all)
                whereClause,    // The columns for the WHERE clause
                whereArgs,      // The values for the WHERE clause
                null,  // don't group the rows
                null,   // don't filter by row groups
                null   // The sort order
        );
    }

    public int update(String TABLE_NAME, ContentValues values, String whereClause, String[] whereArgs){
        Log.i(Names.logTAG, "Inside update function");
        //values = {key1: "", key2: "", key3: ""}, whereClause = Names.UID + " = ?", whereArgs = { num as string}
        //values = {key1: "", key2: "", key3: ""}, whereClause = Names.TID + " = ?", whereArgs = { num as string}
        return this.DBHelper.getWritableDatabase().update(TABLE_NAME, values, whereClause, whereArgs);
    }

    public int delete(String TABLE_NAME, String whereClause, String[] whereArgs){
        Log.i(Names.logTAG, "Inside update function");
        //whereClause = Names.UID + " = ?", whereArgs = { num as string}
        //whereClause = Names.TID + " = ?", whereArgs = { num as string}
        return this.DBHelper.getWritableDatabase().delete(TABLE_NAME,whereClause, whereArgs);
    }

    public int getDataFromProfileTable(Cursor cursor) throws CostManagerException{
        Log.i(Names.logTAG, "Inside getDataFromProfileTable function");
        int res = -1;
        try{
            while (cursor.moveToNext()) {
                res = cursor.getInt(cursor.getColumnIndex(Names.UID));
            }
            cursor.close();
        } catch (CursorIndexOutOfBoundsException e){
            Log.i(Names.logTAG, "error in getDataFromProfileTable function");
            throw new CostManagerException(e.getMessage(), e.getCause());
        }
        return res;
    }

    public JSONObject getDataFromTransactionsTable(Cursor cursor) throws CostManagerException{
        Log.i(Names.logTAG, "Inside getDataFromTransactionsTable function");
        JSONObject res = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            while (cursor.moveToNext()){
                JSONObject jo = new JSONObject();
                jo.put(Names.Date, cursor.getString(cursor.getColumnIndex(Names.Date)));
                jo.put(Names.TName, cursor.getString(cursor.getColumnIndex(Names.TName)));
                jo.put(Names.Category, cursor.getString(cursor.getColumnIndex(Names.Category)));
                jo.put(Names.Amount, cursor.getInt(cursor.getColumnIndex(Names.Amount)));
                jo.put(Names.Price, cursor.getDouble(cursor.getColumnIndex(Names.Price)));
                jo.put(Names.Currency, cursor.getString(cursor.getColumnIndex(Names.Currency)));
                jo.put(Names.Description, cursor.getString(cursor.getColumnIndex(Names.Description)));
                jo.put(Names.isIncome, cursor.getString(cursor.getColumnIndex(Names.isIncome)));
                jo.put(Names.PaymentType, cursor.getString(cursor.getColumnIndex(Names.PaymentType)));
                jo.put(Names.TID, cursor.getInt(cursor.getColumnIndex(Names.TID)));
                ja.put(jo);
            }
            cursor.close();
            res.put("array", ja);
            } catch (JSONException | CursorIndexOutOfBoundsException e) {
                Log.e(Names.logTAG, "error in getDataFromTransactionsTable function", e.getCause());
                throw new CostManagerException(e.getMessage(), e.getCause());
        }
        return res;
    }
}
package com.lironprojects.costmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.lironprojects.costmanager.Models.CostManagerException;
import com.lironprojects.costmanager.Models.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Manage sql lite db such as insert, get, update and delete using the SQLiteBasic helper.
 *
 * @see SQLiteBasic
 * @author Liron Revah and Or Ohana
 */
public class CostManagerDB{
    // local variable
    private SQLiteBasic DBHelper;
    private Context context;

    /**
     * Creating SQLiteBasic helper.
     * @param context Application Context
     * @see SQLiteBasic
     */
    public CostManagerDB(Context context){
        this.DBHelper = new SQLiteBasic(context);
        this.context = context;
    }

    /**
     * Insert the parameters given into the db.
     * placing all parameters into ContentValues before sending them into db.
     *
     * @param name Name of the user.
     * @param password Password of the user.
     * @param email Email of the user.
     * @return user id as number > 0 if insert successfully, else -1
     */
    public long insertToProfileTable(String name, String password, String email) {
        Log.i(Names.logTAG, "Inside insertToProfileTable function");
        ContentValues values = new ContentValues();
        values.put(Names.Name, name);
        values.put(Names.Password, password);
        values.put(Names.Email, email);
        try{
        return this.DBHelper.getWritableDatabase().insert(Names.Profile_Table,
                                                            null, values);
        }catch (SQLiteConstraintException e){
            Message.message(this.context, "Email already used.");
            return -1;
        }
    }

    /**
     *
     * Insert the parameters given into the db.
     * placing all parameters into ContentValues before sending them into db.
     *
     * @param id as user id.
     * @param date String of the transaction date.
     * @param amount number of items in the transaction.
     * @param name name of the transaction.
     * @param price price of the transaction.
     * @param isIncome boolean T/F showing if the transaction is income or expense.
     * @param category category of the transaction.
     * @param currency currency of the transaction, like USD and NIS.
     * @param description user input on the transaction.
     * @param paymentType payment Type such ad cash and credit.
     */
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

    /**
     * query the db using given parameters.
     *
     * @param TABLE_NAME the table name to get the information from.
     * @param columns the columns of the table to return.
     * @param whereClause the columns to filter the result.
     * @param whereArgs the values for the clause to used to filter.
     * @return Cursor contain the results from the query made.
     */
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

    /**
     * update certain rows on the db depends on given parameters.
     *
     * @param TABLE_NAME the table name to update information on.
     * @param values new values to put in the rows.
     * @param whereClause the columns to filter the result.
     * @param whereArgs the values for the clause to used to filter.
     * @return number greater then 0 if updated successfully, else -1.
     */
    public int update(String TABLE_NAME, ContentValues values, String whereClause, String[] whereArgs){
        Log.i(Names.logTAG, "Inside update function");
        //values = {key1: "", key2: "", key3: ""}, whereClause = Names.UID + " = ?", whereArgs = { num as string}
        //values = {key1: "", key2: "", key3: ""}, whereClause = Names.TID + " = ?", whereArgs = { num as string}
        return this.DBHelper.getWritableDatabase().update(TABLE_NAME, values, whereClause, whereArgs);
    }

    /**
     * delete certain rows on the db depends on given parameters.
     *
     * @param TABLE_NAME the table name to delete information from.
     * @param whereClause the columns to filter the result.
     * @param whereArgs the values for the clause to used to filter.
     * @return number greater then 0 if deleted successfully, else -1.
     */
    public int delete(String TABLE_NAME, String whereClause, String[] whereArgs){
        Log.i(Names.logTAG, "Inside update function");
        //whereClause = Names.UID + " = ?", whereArgs = { num as string}
        //whereClause = Names.TID + " = ?", whereArgs = { num as string}
        return this.DBHelper.getWritableDatabase().delete(TABLE_NAME,whereClause, whereArgs);
    }

    /**
     * used only for login to the system.
     *
     * @param cursor from the query made.
     * @return user id number greater than 0 if exist, else -1.
     * @throws CostManagerException if error accord.
     */
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

    /**
     *
     * @param cursor from the query made.
     * @return all the transaction info from the query made.
     *          Example: "array': {{{"key1": value1, "key2": value2, ...}}, {...}, {...}}
     * @throws CostManagerException if JSONException or CursorIndexOutOfBoundsException error may accord.
     */
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
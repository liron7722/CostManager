package com.lironprojects.costmanager.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.lironprojects.costmanager.DB.CostManagerDB;
import com.lironprojects.costmanager.DB.Names;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the model in our MVVM design.
 * Handle all the requests, control the db.
 *
 * @author Liron Revah and Or Ohana
 */
public class RequestHandler {
    private CostManagerDB db;
    private JSONObject response;

    /**
     * create db.
     * @param context Application Context
     */
    public RequestHandler(Context context){
        this.db = new CostManagerDB(context);
    }

    /**
     * simple getter
     * @return response after handling the request.
     */
    public JSONObject getResponse() {
        return response;
    }

    /**
     * simple setter.
     * reset the response after using it.
     */
    public void resetResponse(){
        response = null;
    }

    /**
     * call for another handleRequest after changing request string to json object request.
     *
     * @param request the request as json.toString()
     * @param id user id
     * @throws CostManagerException if error accord.
     */
    public void handleRequest(String request, int id) throws CostManagerException {
        try{
            handleRequest(new JSONObject(request), id);
        } catch (JSONException e) {
            this.response = new JSONObject();
            Log.e(Names.logTAG, "error in handleRequest function - JSONException", e.getCause());
            throw new CostManagerException(e.getMessage(), e.getCause());
        }
    }

    /**
     *
     * @param request the request as json object ready to be used
     * @param id user id
     * @throws CostManagerException if error accord.
     */
    public void handleRequest(JSONObject request, int id) throws CostManagerException{
        Log.i(Names.logTAG, "Inside handleRequest function");
        JSONObject response = new JSONObject();
        /*
            switch case using the values of the cmd and table key to identify why function to run.
            creating the response for that request.
         */
        try {
            String cmd = request.getString(Names.Command);
            String table = request.getString(Names.Table);
            JSONObject requestData = new JSONObject(request.getString(Names.Data));
            int result;
            Log.i(Names.logTAG, "Got " + cmd +" Command");
            switch (cmd) {
                case "insert":
                    Log.i(Names.logTAG, "Trying to insert into " + table + " table");
                    switch (table) {
                        case Names.Profile_Table:
                            result = (int) db.insertToProfileTable(requestData.getString(Names.Name),
                                                            requestData.getString(Names.Password),
                                                            requestData.getString(Names.Email));
                            response.put(Names.newID, result);
                            if (result > 0){
                                response.put(Names.ResultMsg, "Register Success");
                                response.put(Names.Data, true);
                            } else {
                                response.put(Names.ResultMsg, "Register Failed");
                                response.put(Names.Data, false);
                            }
                            break;
                        case Names.Transactions_Table:
                            if (requestData.getBoolean(Names.isRepeat) && requestData.getInt(Names.Repeat) > 0)
                                for (int i = 1; i <= requestData.getInt(Names.Repeat); i++)
                                    db.insertToTransactionTable(id,
                                            requestData.getString(Names.Date),
                                            requestData.getInt(Names.Amount),
                                            requestData.getString(Names.TName) + " - " + i,
                                            requestData.getDouble(Names.Price),
                                            requestData.getBoolean(Names.isIncome),
                                            requestData.getString(Names.Category),
                                            requestData.getString(Names.Currency),
                                            requestData.getString(Names.Description),
                                            requestData.getString(Names.PaymentType));
                            else
                                db.insertToTransactionTable(id,
                                    requestData.getString(Names.Date),
                                    requestData.getInt(Names.Amount),
                                    requestData.getString(Names.TName),
                                    requestData.getDouble(Names.Price),
                                    requestData.getBoolean(Names.isIncome),
                                    requestData.getString(Names.Category),
                                    requestData.getString(Names.Currency),
                                    requestData.getString(Names.Description),
                                    requestData.getString(Names.PaymentType));
                            response.put(Names.ResultMsg, "Added successfully");
                            response.put(Names.Data, true);
                            break;
                    } break;

                case "get":
                    Cursor cursor;
                    Log.i(Names.logTAG, "Trying to get info from " + table + " table");
                    switch (table) {
                        case Names.Profile_Table:
                            cursor = db.query(table,
                                    new String[]{requestData.get("columns").toString()},
                                    requestData.getString("whereClause"),
                                    splitStrings(requestData.get("whereArgs").toString()));
                            result = db.getDataFromProfileTable(cursor);
                            response.put(Names.newID, result);
                            if (result > 0){
                                response.put(Names.ResultMsg, "Logged In");
                                response.put(Names.Data, true);
                            } else {
                                response.put(Names.ResultMsg, "Logging Failed");
                                response.put(Names.Data, false);
                            }
                            break;
                        case Names.Transactions_Table:
                            cursor = db.query(table,
                                    null,
                                    requestData.getString("whereClause"),
                                    new String[]{Integer.toString(id)});
                            JSONObject res = db.getDataFromTransactionsTable(cursor);
                            response.put(Names.Data, res.toString());
                            break;
                    }break;

                case "update":
                    result = db.update(table, (ContentValues) requestData.get("values"), requestData.getString("whereClause"), (String[]) requestData.get("whereArgs"));
                    response.put(Names.ResultMsg, "Updated successfully");
                    response.put(Names.Data, result);
                    break;

                case "delete":
                    db.delete(table, requestData.getString("whereClause"), (String[]) requestData.get("whereArgs"));
                    response.put(Names.ResultMsg, "Deleted successfully");
                    break;
            }
            this.response = response;
        }catch (JSONException e){
            couldNotResponse();
            Log.e(Names.logTAG, "error in handleRequest function", e.getCause());
            throw new CostManagerException(e.getMessage(), e.getCause());
        }
    }

    /**
     *
     * @throws CostManagerException if JSONException error may accord.
     */
    private void couldNotResponse() throws CostManagerException{
        JSONObject response = new JSONObject();
        try {
            response.put(Names.ResultMsg, "Failed");
            response.put(Names.Data, false);
            this.response = response;
        }catch (JSONException e) {
            this.response = response;
            Log.e(Names.logTAG, "error in couldNotResponse function", e.getCause());
            throw new CostManagerException(e.getMessage(), e.getCause());
        }
    }

    /**
     * separated values using regex
     * @param toSplit the string contain values to be separated.
     * @return separated values in string array.
     */
    private String[] splitStrings(String toSplit){
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile("([^\\W_]+[^\\s,]*)")
                .matcher(toSplit);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches.toArray(new String[]{});
    }
}

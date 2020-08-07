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

public class RequestHandler {
    private CostManagerDB db;
    private JSONObject response;

    public RequestHandler(Context context){
        this.db = new CostManagerDB(context);
    }

    public JSONObject getResponse() {
        return response;
    }

    public void resetResponse(){
        response = null;
    }

    public void handleRequest(String request, int id) throws CostManagerException {
        try{
            handleRequest(new JSONObject(request), id);
        } catch (JSONException e) {
            this.response = new JSONObject();
            Log.e(Names.logTAG, "error in handleRequest function - JSONException", e.getCause());
            throw new CostManagerException(e.getMessage(), e.getCause());
        }
    }

    public void handleRequest(JSONObject request, int id) throws CostManagerException{
        Log.i(Names.logTAG, "Inside handleRequest function");
        JSONObject response = new JSONObject();
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
            this.response = couldNotResponse();
            Log.e(Names.logTAG, "error in handleRequest function", e.getCause());
            throw new CostManagerException(e.getMessage(), e.getCause());
        }
    }

    private JSONObject couldNotResponse() throws CostManagerException{
        JSONObject response = new JSONObject();
        try {
            response.put(Names.ResultMsg, "Failed");
            response.put(Names.Data, false);
        }catch (JSONException e) {
            Log.e(Names.logTAG, "error in couldNotResponse function", e.getCause());
            throw new CostManagerException(e.getMessage(), e.getCause());
        }
        return response;
    }

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

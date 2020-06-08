package com.lironprojects.costmanager.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.lironprojects.costmanager.DB.CostManagerDB;
import com.lironprojects.costmanager.DB.Names;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {
    private CostManagerDB db;

    public RequestHandler(Context context){
        this.db = new CostManagerDB(context);
    }

    public JSONObject handleRequest(String request, int id) throws ProductsException{
        try{
            return handleRequest(new JSONObject(request), id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject(); // TODO get proper response
    }

    private JSONObject handleRequest(JSONObject request, int id) {
        JSONObject response = new JSONObject();
        try {
            String cmd = request.getString(Names.Command);
            String table = request.getString(Names.Table);
            JSONObject requestData = new JSONObject(request.getString(Names.Data));
            long result;
            switch (cmd) {
                case "insert":
                    switch (table) {
                        case Names.Profile_Table:
                            result = db.insertToProfileTable(requestData.getString(Names.Name),
                                                            requestData.getString(Names.Password),
                                                            requestData.getString(Names.Email));
                            response.put(Names.ResultMsg, "Register Success");
                            response.put(Names.URL, "home");
                            response.put(Names.Data, result);
                            break;
                        case Names.Transactions_Table:
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
                            response.put(Names.ResultMsg, "Success");
                            response.put(Names.URL, "home");
                            response.put(Names.Data, new JSONObject("{true}"));
                            break;
                    } break;

                case "get":
                    Cursor cursor;
                    response.put(Names.ResultMsg, "#");
                    switch (table) {
                        case Names.Profile_Table:
                            cursor = db.query(table,
                                    null,//new String[]{requestData.get("columns").toString()},
                                    requestData.getString("whereClause"),
                                    splitStrings(requestData.get("whereArgs").toString()));
                            response.put(Names.newID, db.getDataFromProfileTable(cursor));
                            break;

                        case Names.Transactions_Table:
                            cursor = db.query(table,
                                    null,
                                    requestData.getString("whereClause"),
                                    new String[]{Integer.toString(id)});
                            response.put(Names.Data, db.getDataFromTransactionsTable(cursor));
                            break;
                    }break;

                case "update":
                    result = db.update(table, (ContentValues) requestData.get("values"), requestData.getString("whereClause"), (String[]) requestData.get("whereArgs"));
                    response.put(Names.ResultMsg, "Update Success");
                    response.put(Names.URL, "home");
                    response.put(Names.Data, result);
                    break;

                case "delete":
                    result = db.delete(table, requestData.getString("whereClause"), (String[]) requestData.get("whereArgs"));
                    response.put(Names.ResultMsg, "Delete Success");
                    break;
            }
        }catch (JSONException e){
            response = couldNotResponse();
        }
        return response;
    }

        private void setResponse(JSONObject response, long result) throws JSONException {
        boolean success = result > 0;
        response.put(Names.ResultMsg, "Success");
        response.put(Names.URL, "Data");
        response.put(Names.Data, result);
    }

    private JSONObject couldNotResponse(){
        JSONObject response = new JSONObject();
        try {
            response.put(Names.ResultMsg, "Failed");
            response.put(Names.URL, "#");
            response.put(Names.Data, "#"); // TODO Change
        }catch (JSONException e) {
            System.out.println("Could not create response Json");
        }
        return response;
    }

    private String[] splitStrings(String toSplit){
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\\W_]+[^\\s,]*)")
                .matcher(toSplit);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches.toArray(new String[]{});
    }
}


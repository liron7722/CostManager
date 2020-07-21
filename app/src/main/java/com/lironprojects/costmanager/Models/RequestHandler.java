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

    public void handleRequest(String request, int id) throws ProductsException{
        try{
            handleRequest(new JSONObject(request), id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.response = new JSONObject(); // TODO get proper response
    }

    public void handleRequest(JSONObject request, int id) {
        JSONObject response = new JSONObject();
        try {
            String cmd = request.getString(Names.Command);
            String table = request.getString(Names.Table);
            JSONObject requestData = new JSONObject(request.getString(Names.Data));
            int result;
            switch (cmd) {
                case "insert":
                    System.out.println("Got insert Command");
                    switch (table) {
                        case Names.Profile_Table:
                            System.out.println("Trying to insert into profile table");
                            result = (int) db.insertToProfileTable(requestData.getString(Names.Name),
                                                            requestData.getString(Names.Password),
                                                            requestData.getString(Names.Email));
                            response.put(Names.newID, result);
                            if (result > 0){
                                response.put(Names.ResultMsg, "Register Success");
                                response.put(Names.Data, "true");
                            } else {
                                response.put(Names.ResultMsg, "Register Failed");
                                response.put(Names.Data, "false");
                            }
                            break;
                        case Names.Transactions_Table:


                            System.out.println("Trying to insert into Transactions Table");
                            if (requestData.getBoolean(Names.isRepeat))
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
                            response.put(Names.ResultMsg, "Success");
                            response.put(Names.Data, "true");
                            break;
                    } break;

                case "get":
                    System.out.println("Got get Command");
                    Cursor cursor;
                    switch (table) {
                        case Names.Profile_Table:
                            System.out.println("Trying to get info from profile Table");
                            cursor = db.query(table,
                                    null,//new String[]{requestData.get("columns").toString()},
                                    requestData.getString("whereClause"),
                                    splitStrings(requestData.get("whereArgs").toString()));
                            result = db.getDataFromProfileTable(cursor);
                            response.put(Names.newID, result);
                            if (result > 0){
                                response.put(Names.ResultMsg, "Logged In");
                                response.put(Names.Data, "true");
                            } else {
                                response.put(Names.ResultMsg, "Logging Failed");
                                response.put(Names.Data, "false");
                            }
                            break;

                        case Names.Transactions_Table:
                            System.out.println("Trying to get info from Transactions Table");
                            cursor = db.query(table,
                                    null,
                                    requestData.getString("whereClause"),
                                    new String[]{Integer.toString(id)});
                            JSONObject res = db.getDataFromTransactionsTable(cursor);
                            response.put(Names.Data, res.toString());
                            break;
                    }break;

                case "update":
                    System.out.println("Got update Command");
                    result = db.update(table, (ContentValues) requestData.get("values"), requestData.getString("whereClause"), (String[]) requestData.get("whereArgs"));
                    response.put(Names.ResultMsg, "Update Success");
                    response.put(Names.URL, "home");
                    response.put(Names.Data, result);
                    break;

                case "delete":
                    System.out.println("Got delete Command");
                    db.delete(table, requestData.getString("whereClause"), (String[]) requestData.get("whereArgs"));
                    response.put(Names.ResultMsg, "Delete Success");
                    break;
            }
        }catch (JSONException e){
            response = couldNotResponse();
        }
        System.out.println("Printing out going response");
        System.out.println(response.toString());
        this.response =  response;
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
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile("([^\\W_]+[^\\s,]*)")
                .matcher(toSplit);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches.toArray(new String[]{});
    }
}

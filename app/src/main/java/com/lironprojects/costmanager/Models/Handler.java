package com.lironprojects.costmanager.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.lironprojects.costmanager.DB.CostManagerDB;
import com.lironprojects.costmanager.DB.Names;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Handler {
    private CostManagerDB db;
    private static final String Command = "cmd";
    private static final String Table = "table";
    private static final String Data = "data";

    private static final String Result = "data";
    private static final String Type = "type";

    Handler(Context context){
        this.db = new CostManagerDB(context);
    }

    public JSONObject handleRequest(JSONObject request) {
        JSONObject response = new JSONObject();
        try {
            String cmd = request.getString(Command);
            String table = request.getString(Table);
            JSONObject data;
            JSONArray aData;
            JSONObject requestData = request.getJSONObject(Data);
            long result;
            switch (cmd) {
                case "set":
                    switch (table) {
                        case Names.Profile_Table:
                            result = db.insertToProfileTable(requestData.getString(Names.Name), requestData.getString(Names.Password), requestData.getString(Names.Email));
                            setResponse(response, result);
                            break;
                        case Names.Settings_Table:
                            result = db.insertToSettingsTable(requestData.getInt(Names.UID), requestData.getDouble(Names.WeeklyBudget),
                                    requestData.getString(Names.IncomeColor), requestData.getString(Names.ExpensesColor));
                            setResponse(response, result);
                            break;
                        case Names.Transactions_Table:
                            result = db.insertToTransactionTable(requestData.getInt(Names.UID), requestData.getString(Names.Date), requestData.getInt(Names.Amount),
                                    requestData.getString(Names.TName), requestData.getDouble(Names.Price), requestData.getBoolean(Names.isRepeat), requestData.getBoolean(Names.isIncome),
                                    requestData.getString(Names.Category), requestData.getString(Names.Currency), requestData.getString(Names.Description), requestData.getString(Names.PaymentType));
                            setResponse(response, result);
                            break;
                    } break;

                case "get":
                    Cursor cursor = db.query(table, requestData.getString("whereClause"), (String[]) requestData.get("whereArgs"));
                    response.put(Result, true);
                    response.put(Type, "Info");
                    switch (table) {
                        case Names.Profile_Table:
                            data = db.getDataFromProfileTable(cursor);
                            response.put(Data, data);
                            break;
                        case Names.Settings_Table:
                            data = db.getDataFromSettingsTable(cursor);
                            response.put(Data, data);
                            break;
                        case Names.Transactions_Table:
                            aData = db.getDataFromTransactionsTable(cursor);
                            response.put(Data, aData);
                            break;
                    }break;

                case "update":
                    result = db.update(table, (ContentValues) requestData.get("values"), requestData.getString("whereClause"), (String[]) requestData.get("whereArgs"));
                    setResponse(response, result);
                    break;

                case "delete":
                    result = db.delete(table, requestData.getString("whereClause"), (String[]) requestData.get("whereArgs"));
                    setResponse(response, result);
                    break;
            }
        }catch (JSONException e){
            response = couldNotResponse();
        }
        return response;
    }

    private void setResponse(JSONObject response, long result) throws JSONException {
        boolean success = result > 0;
        String message = "Data";
        response.put(Result, success);
        response.put(Type, message);
        response.put(Data, result);
    }

    private JSONObject couldNotResponse(){
        JSONObject response = new JSONObject();
        try {
            response.put(Result, false);
            response.put(Type, "Error");
            response.put(Data, "");
        }catch (JSONException e) {
            System.out.println("Major Crisis");
        }
        return response;
    }

}

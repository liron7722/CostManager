package com.lironprojects.costmanager.ViewModels;

import android.util.Log;
import android.webkit.WebView;
import android.content.Context;
import android.content.SharedPreferences;

import com.lironprojects.costmanager.DB.Names;
import com.lironprojects.costmanager.Models.Message;
import com.lironprojects.costmanager.Models.RequestHandler;
import com.lironprojects.costmanager.Models.CostManagerException;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ViewModel {
    private static final String uID = "id";
    private static final String wB = "WeeklyBudget";
    private static final String iC = "IncomeColor";
    private static final String eC = "ExpensesColor";

    private static final String welcomeURL = "file:///android_asset/templates/welcome.html";
    private static final String homeURL = "file:///android_asset/templates/home.html";

    private WebView view;
    private Context context;
    private SharedPreferences sp;
    private RequestHandler handler;
    private ExecutorService service = Executors.newFixedThreadPool(4);

    public void setView(WebView v){
        this.view = v;
        this.view.loadUrl(homeURL);
    }
    public void setContext(Context c){
        this.context = c;
    }
    public void setSP(SharedPreferences sp){
        this.sp = sp;
    }
    public void setModel(RequestHandler model){
        this.handler = model;
    }

    @android.webkit.JavascriptInterface
    public void handlingError(String message){
        Log.e(Names.logTAG, message);
        Message.message(this.context, "Error occurred in JavaScript");
    }

    @android.webkit.JavascriptInterface
    public void previous(){
        this.view.goBack();
    }

    @android.webkit.JavascriptInterface
    public boolean loginStatus(){
        return sp.getInt(uID, -1) > 0;
    }

    @android.webkit.JavascriptInterface
    public void logOut(){
        this.sp.edit().putInt(uID,-1).apply();
        Message.message(this.context,"Logged out successfully");
    }

    @android.webkit.JavascriptInterface
    public boolean updateSettings(String key, String value) {
        switch (key) {
            case wB:
                this.sp.edit().putInt(wB,Integer.parseInt(value)).apply();
                break;
            case iC:
                this.sp.edit().putString(iC,value).apply();
                break;
            case eC:
                this.sp.edit().putString(eC,value).apply();
                break;
        }
        return true;
    }

    @android.webkit.JavascriptInterface
    public String getSettings(String key) {
        String result = "";
        switch (key) {
            case uID:
                result = String.valueOf(sp.getInt(uID, -1));
                break;
            case wB:
                result = String.valueOf(sp.getInt(wB, 0));
                break;
            case iC:
                result  = sp.getString(iC, "green");
                break;
            case eC:
                result  = sp.getString(eC, "red");
                break;
        }
        return result;
    }

    @android.webkit.JavascriptInterface
    public void Request(final String request) {
        final String data;
        this.service.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.i(Names.logTAG, "Inside service.submit");
                    int id = Integer.parseInt(getSettings(uID));
                    handler.handleRequest(request, id);
                }catch (CostManagerException e){
                    Log.e(Names.logTAG, "Got error handling the request", e.getCause());
                }
            }
        });
        try {
            Log.i(Names.logTAG, "After service.submit");

            JSONObject response = handler.getResponse();
            while (response == null)
                response = handler.getResponse();

            Log.i(Names.logTAG, "After getResponse");

            // new login
            if (response.has(Names.newID)) {
                System.out.println("Got new ID: " + response.getInt(Names.newID));
                sp.edit().putInt(uID, response.getInt(Names.newID)).apply();
            }

            // make toast
            if (response.has(Names.ResultMsg))
                Message.message(this.context, response.getString(Names.ResultMsg));

            // send requested data
            if (response.has(Names.Data)) {
                data = response.getString(Names.Data);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.evaluateJavascript("handleResponse('" + data + "')",
                                null);
                    }
                });
            }
            handler.resetResponse();
        }catch (JSONException e){
            Log.e(Names.logTAG, "Error getting data from response json", e.getCause());
        }
    }
}

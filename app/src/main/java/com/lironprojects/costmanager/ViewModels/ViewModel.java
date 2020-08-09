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

/**
 * relay the requests from the view to the model.
 * relay the responses from the model to the view.
 * handle toasts shared preferences.
 *
 * @author Liron Revah and Or Ohana
 */
public class ViewModel {
    // strings for local use
    private static final String uID = "id";
    private static final String wB = "WeeklyBudget";
    private static final String iC = "IncomeColor";
    private static final String eC = "ExpensesColor";

    // home page url
    private static final String homeURL = "file:///android_asset/templates/home.html";

    // local variables
    private WebView view;
    private Context context;
    private SharedPreferences sp;
    private RequestHandler handler;
    private ExecutorService service = Executors.newFixedThreadPool(4);

    /**
     * simple setter
     * @param v Application web view that display in screen
     * @see WebView
     */
    public void setView(WebView v){
        this.view = v;
        this.view.loadUrl(homeURL);
    }

    /**
     * simple setter
     * @param c Application Context
     */
    public void setContext(Context c){
        this.context = c;
    }

    /**
     * simple setter
     * @param sp shared preferences.
     * @see SharedPreferences
     */
    public void setSP(SharedPreferences sp){
        this.sp = sp;
    }

    /**
     * simple setter
     * @param model RequestHandler.
     * @see RequestHandler
     */
    public void setModel(RequestHandler model){
        this.handler = model;
    }

    /**
     * log JS message into app log
     * @param message the message from JS code
     */
    @android.webkit.JavascriptInterface
    public void log(String message){
        Log.i(Names.logTAG, message);
    }

    /**
     * handle the errors in the view.
     * log the error for future review
     * @param message contain the error that occur in the view
     */
    @android.webkit.JavascriptInterface
    public void handlingError(String message){
        Log.e(Names.logTAG, message);
        // Message.message(this.context, "Error occurred in JavaScript");
    }

    /**
     * go to previous page after back button pressed in view
     */
    @android.webkit.JavascriptInterface
    public void previous(){
        view.post(new Runnable() {
            @Override
            public void run() {
                view.goBack();
            }
        });

    }

    /**
     * check if user logging in
     * @return T/F if user id is logged or not
     */
    @android.webkit.JavascriptInterface
    public boolean loginStatus(){
        return sp.getInt(uID, -1) > 0;
    }

    /**
     * log user out of the system.
     * display log out toast after.
     */
    @android.webkit.JavascriptInterface
    public void logout(){
        this.sp.edit().putInt(uID,-1).apply();
        Message.message(this.context,"Logged out successfully");
    }

    /**
     *
     * @param key the setting user want to change
     * @param value the new value to save
     * @return true after saved, false if empty parameters send.
     */
    @android.webkit.JavascriptInterface
    public boolean changeSettings(String key, String value) {
        boolean res = false;
        if (value.isEmpty())
            return false;
        switch (key) {
            case wB:
                this.sp.edit().putInt(wB,Integer.parseInt(value)).apply();
                res = true;
                break;
            case iC:
                this.sp.edit().putString(iC,value).apply();
                res = true;
                break;
            case eC:
                this.sp.edit().putString(eC,value).apply();
                res = true;
                break;
        }
        if (res)
            Message.message(this.context, "Update successfully");
        else
            Message.message(this.context, "Update failed");
        return res;
    }

    /**
     *
     * @param key the setting user want to see.
     * @return value of the requested key, empty if key not exist.
     */
    @android.webkit.JavascriptInterface
    public String getSettings(String key) {
        switch (key) {
            case uID:
                return String.valueOf(sp.getInt(uID, -1));
            case wB:
                return String.valueOf(sp.getInt(wB, 0));
            case iC:
                return sp.getString(iC, "rgba(41, 241, 195, 1)");
            case eC:
                return sp.getString(eC, "rgba(246, 36, 89, 1)");
        }
        return "";
    }

    /**
     *
     * @param request the user request. contain json object as string.
     */
    @android.webkit.JavascriptInterface
    public void Request(final String request) {
        final String data;

        /*
            getting user id.
            running new thread executor to handle the request.
         */
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

        /*
            handling the response made.
            make toast if needed.
            send view information requested.
         */
        try {
            Log.i(Names.logTAG, "After service.submit");

            // loop waiting for the response be ready in the other thread.
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

                // send response data in view thread.
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.evaluateJavascript("handleResponse('" + data + "')",
                                null);
                    }
                });
            }

            handler.resetResponse(); // reset the saved response.
        }catch (JSONException e){
            Log.e(Names.logTAG, "Error getting data from response json", e.getCause());
        }
    }
}

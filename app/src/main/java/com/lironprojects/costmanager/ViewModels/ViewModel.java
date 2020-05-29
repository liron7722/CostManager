package com.lironprojects.costmanager.ViewModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.Toast;

import com.lironprojects.costmanager.Models.RequestHandler;

public class ViewModel {
    private static final String localSP ="com.lironprojects.costmanager.Settings";
    private static final String uID = "id";
    private static final String wB = "WeeklyBudget";
    private static final String iC = "IncomeColor";
    private static final String eC = "ExpensesColor";

    private static final String loginURL = "file:///android_asset/templates/login.html";
    private static final String homeURL = "file:///android_asset/templates/home.html";

    private WebView view;
    private final RequestHandler handler;
    private final Toast toast;
    private SharedPreferences sp;

    public ViewModel(Context context, WebView view, RequestHandler handler){
        this.view = view;
        this.handler = handler;
        this.toast = Toast.makeText(context, "", Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
        this.sp = context.getSharedPreferences(localSP , Context.MODE_PRIVATE);
        loadStartPage();
    }


    @android.webkit.JavascriptInterface
    public void loadStartPage(){
        if (Integer.parseInt(getSettings(uID)) != -1)
            this.view.loadUrl(homeURL);
        else
            this.view.loadUrl(loginURL);
    }

    @android.webkit.JavascriptInterface
    public boolean updateSettings(String key, int value) {
        switch (key) {
            case uID:
                this.sp.edit().putInt(uID,value).apply();
                break;
            case wB:
                this.sp.edit().putInt(wB,value).apply();
                break;
        }
        return true;
    }

    @android.webkit.JavascriptInterface
    public boolean updateSettings(String key, String value) {
        switch (key) {
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
    public String Request(String stringRequest) {
        return this.handler.handleRequest(stringRequest).toString();
    }

    @android.webkit.JavascriptInterface
    public void Toast(String message) {
        this.toast.setText(message);
        this.toast.show();
    }

    @android.webkit.JavascriptInterface
    public boolean updatePreferences(String stringRequest){ return true;}

}

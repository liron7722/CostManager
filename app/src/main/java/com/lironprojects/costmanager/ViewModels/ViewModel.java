package com.lironprojects.costmanager.ViewModels;

import android.os.AsyncTask;
import android.webkit.WebView;

import com.lironprojects.costmanager.Models.RequestHandler;

import org.json.JSONObject;

public class ViewModel {
    private final WebView view;
    private final RequestHandler handler;

    public ViewModel(WebView view, RequestHandler handler){
        this.view = view;
        this.handler = handler;
    }

    public JSONObject Request(JSONObject request) {
        return this.handler.handleRequest(request);
    }

}

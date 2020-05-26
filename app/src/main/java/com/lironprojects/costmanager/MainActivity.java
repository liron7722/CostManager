package com.lironprojects.costmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import com.lironprojects.costmanager.Models.RequestHandler;
import com.lironprojects.costmanager.ViewModels.ViewModel;

public class MainActivity extends AppCompatActivity {
private final static String indexURL = "file:///android_asset/templates/home.html";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview);
        WebView web = findViewById(R.id.WebView);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(indexURL);

        RequestHandler rh =  new RequestHandler(this);
        ViewModel vm = new ViewModel(web, rh);

        setJSInterface(web, vm);
    }

    @SuppressLint("JavascriptInterface")
    private void setJSInterface(WebView view, ViewModel handler){
        view.addJavascriptInterface(handler, "handler");
    }
}

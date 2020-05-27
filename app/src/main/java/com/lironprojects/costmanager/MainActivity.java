package com.lironprojects.costmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lironprojects.costmanager.Models.RequestHandler;
import com.lironprojects.costmanager.ViewModels.ViewModel;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
private final static String indexURL = "file:///android_asset/templates/home.html";
private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview);
        this.webView = new WebView(this);
        RequestHandler rh =  new RequestHandler(this);
        ViewModel vm = new ViewModel(webView, rh);
        webView.loadUrl(indexURL);
        webView.addJavascriptInterface(vm, "vm");
        webView.setWebViewClient(new WebViewClient());
        setContentView(webView);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.webView.getSettings().setJavaScriptEnabled(false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onResume()
    {
        super.onResume();
        this.webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}

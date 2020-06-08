package com.lironprojects.costmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lironprojects.costmanager.Models.RequestHandler;
import com.lironprojects.costmanager.ViewModels.ViewModel;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private static final String localSP ="com.lironprojects.costmanager.Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.webView = new WebView(this);
        this.webView.setWebViewClient(new WebViewClient());

        ViewModel vm = new ViewModel();
        vm.setModel(new RequestHandler(this));
        vm.setView(this.webView);
        vm.setToast(Toast.makeText(this, "", Toast.LENGTH_SHORT));
        vm.setSP(getSharedPreferences(localSP , Context.MODE_PRIVATE));
        vm.loadStartPage();

        this.webView.addJavascriptInterface(vm, "vm");
        setContentView(this.webView);

    }

    @Override
    public void onPause() {
        super.onPause();
        this.webView.getSettings().setJavaScriptEnabled(false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onResume(){
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

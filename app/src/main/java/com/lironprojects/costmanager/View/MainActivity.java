package com.lironprojects.costmanager.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lironprojects.costmanager.Models.RequestHandler;
import com.lironprojects.costmanager.ViewModels.ViewModel;

/**
 * MainActivity for the app.
 * web view as view, viewModel that control the flow between view and model and model control the db.
 *
 * @author Liron Revah and Or Ohana
 */
public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private static final String localSP ="com.lironprojects.costmanager.Settings";

    /**
     * create web view as view, viewModel and model.
     * connect between view and model to viewModel.
     * add viewModel as web view js interface.
     *
     * @param savedInstanceState Instance State of the app
     * @see WebView
     * @see RequestHandler
     * @see ViewModel
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.webView = new WebView(this);
        this.webView.setWebViewClient(new WebViewClient());

        ViewModel vm = new ViewModel();
        vm.setModel(new RequestHandler(this));
        vm.setView(this.webView);
        vm.setContext(this.getApplicationContext());
        vm.setSP(getSharedPreferences(localSP , Context.MODE_PRIVATE));

        this.webView.addJavascriptInterface(vm, "vm");
        setContentView(this.webView);
    }

    /**
     * disable js on web view.
     * pause the app.
     */
    @Override
    public void onPause() {
        this.webView.getSettings().setJavaScriptEnabled(false);
        super.onPause();
    }

    /**
     * enable js on web view.
     * resume the app.
     */
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onResume(){
        super.onResume();
        this.webView.getSettings().setJavaScriptEnabled(true);
    }

    /**
     *
     * @param keyCode the code of the event occur.
     * @param event the event to make.
     * @return true if user press back button and web view have previous page.
     */
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

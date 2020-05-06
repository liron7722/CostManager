package com.lironprojects.costmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import com.lironprojects.costmanager.DB.CostManagerDB;
import com.lironprojects.costmanager.DB.Names;

public class MainActivity extends AppCompatActivity {
private final static String indexURL = "file:///android_asset/index.html";
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        WebView web = findViewById(R.id.WebView);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(indexURL);

        //CostManagerDB db = new CostManagerDB(this);
        //long id = db.insertToProfileTable("", "", "");
        //int id = db.delete("Profile", "_id like ?" , new String[]{"9"});
        //System.out.println(id);
    }
}

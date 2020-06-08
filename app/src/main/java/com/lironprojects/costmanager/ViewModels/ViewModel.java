package com.lironprojects.costmanager.ViewModels;

import android.content.SharedPreferences;
import android.webkit.WebView;
import android.widget.Toast;

import com.lironprojects.costmanager.DB.Names;
import com.lironprojects.costmanager.Models.RequestHandler;
import com.lironprojects.costmanager.Models.ProductsException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel {
    private static final String uID = "id";
    private static final String wB = "WeeklyBudget";
    private static final String iC = "IncomeColor";
    private static final String eC = "ExpensesColor";

    private static final String loginURL = "file:///android_asset/templates/login.html";
    private static final String homeURL = "file:///android_asset/templates/home.html";

    private WebView view;
    private Toast toast;
    private SharedPreferences sp;
    private RequestHandler handler;
    ExecutorService service = Executors.newFixedThreadPool(4);

    public void setView(WebView v){
        this.view = v;
    }
    public void setToast(Toast t){
        this.toast = t;
    }
    public void setSP(SharedPreferences sp){
        this.sp = sp;
    }
    public void setModel(RequestHandler model){
        this.handler = model;
    }

    public void Toast(String message) {
        if (!message.equals("#")){
            this.toast.setText(message);
            this.toast.show();
        }
    }

    @android.webkit.JavascriptInterface
    public void loadStartPage(){
        if (Integer.parseInt(getSettings(uID)) != -1)
            this.view.loadUrl(homeURL);
        else
            this.view.loadUrl(loginURL);
    }

    @android.webkit.JavascriptInterface
    public void logOut(){
        this.sp.edit().putInt(uID,-1).apply();
        loadStartPage();
        Toast("logged out successfully");
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
        final String[] myURL = {"", ""};
        this.service.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    int id = Integer.parseInt(getSettings(uID));
                    JSONObject res = handler.handleRequest(request, id);

                    // make toast
                    Toast(res.getString(Names.ResultMsg));

                    // new login
                    if (res.has(Names.newID)){
                        sp.edit().putInt(uID,res.getInt(Names.newID)).apply();
                    }

                    // change url if needed
                    if (res.has(Names.URL)) {
                        myURL[0] = res.getString(Names.URL);
                    }

                    // call js function if needed
                    if (res.has(Names.Data))
                        myURL[1] = "handleResponse(" + res.getString(Names.Data) + ")";


                }catch (ProductsException | JSONException e){
                    //TODO catch
                    Toast("Error was made");
                }
            }
        });
        // change url if needed
        if (myURL[0].length() > 0){
            if(myURL[0].equals("home"))
                loadStartPage();
            else if(myURL[0].equals("back"))
                view.goBack();
        }
        if (myURL[1].length() > 0){
            view.evaluateJavascript(myURL[1], null);
        }
    }




    @android.webkit.JavascriptInterface
    public void test() throws JSONException {
        final JSONObject req = new JSONObject();
        JSONObject data = new JSONObject();

        data.put(Names.UID, 1);
        data.put(Names.Date, "30/05/2020");
        data.put(Names.Amount, 1);
        data.put(Names.TName, "test1");
        data.put(Names.Price, 50.20);
        data.put(Names.isIncome, false);
        data.put(Names.Category, "Category test");
        data.put(Names.Currency, "NIS");
        data.put(Names.Description, "this is my desc");
        data.put(Names.PaymentType, "cash");

        req.put("cmd", "insert");
        req.put("table", Names.Transactions_Table);
        req.put("data", data.toString());

        //JSONObject result = this.handler.handleRequest(req.toString());
        //System.out.println(result.getBoolean("data"));
        this.service.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject res = handler.handleRequest(req.toString(), 1);
                    System.out.println(res.getString("data"));
                    Toast(res.getString("data"));
                }catch (ProductsException | JSONException e){
                    //TODO catch
                }
            }
        });
        /*MyAsyncTask async = new MyAsyncTask(this.handler, this.view.getContext());
        async.execute(req.toString());
        String s = this.handler.getData().getString("data");
        Toast(s);*/
    }
}

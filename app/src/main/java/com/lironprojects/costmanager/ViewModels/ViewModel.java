package com.lironprojects.costmanager.ViewModels;

import android.webkit.WebView;
import android.content.Context;
import android.content.SharedPreferences;

import com.lironprojects.costmanager.DB.Names;
import com.lironprojects.costmanager.Models.Message;
import com.lironprojects.costmanager.Models.RequestHandler;
import com.lironprojects.costmanager.Models.ProductsException;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ViewModel {
    private static final String uID = "id";
    private static final String wB = "WeeklyBudget";
    private static final String iC = "IncomeColor";
    private static final String eC = "ExpensesColor";

    private static final String loginURL = "file:///android_asset/templates/welcome.html";
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
        view.post(new Runnable() {
            @Override
            public void run() {
                view.evaluateJavascript("LoginStatus('" + loginURL + "')", null);
            }});

        Message.message(this.context,"logged out successfully");
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
    public void Request(final String request) throws JSONException, InterruptedException {
        final String data;
        this.service.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("inside service.submit");
                    System.out.println(request);
                    int id = Integer.parseInt(getSettings(uID));
                    handler.handleRequest(request, id);
                }catch (ProductsException e){
                    //TODO catch
                    System.out.println("Error at received response");
                }
            }
        });
        System.out.println("after service.submit");
        //TimeUnit.MILLISECONDS.sleep(1000);  // Give time to handle the request

        JSONObject response = handler.getResponse();
        while (response == null)
            response = handler.getResponse();
        System.out.println(response);
        System.out.println("after handler.getResponse");

        // new login
        if (response.has(Names.newID)){
            System.out.println("Got new ID: " + response.getInt(Names.newID));
            sp.edit().putInt(uID, response.getInt(Names.newID)).apply();
        }

        // make toast
        if (response.has(Names.ResultMsg))
            Message.message(this.context, response.getString(Names.ResultMsg));

        // requested data
        if (response.has(Names.Data)) {
            System.out.println("Got data key in json");
            data = response.getString(Names.Data);

            // Sending data
            System.out.println("sending data: " + data);
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.evaluateJavascript("handleResponse('" + data + "')",
                            null);
                }
            });
        }
        System.out.println("befor handler.resetResponse");
        handler.resetResponse();
        System.out.println("after handler.resetResponse");
    }
/*
    @android.webkit.JavascriptInterface
    public void addTransactionTest() throws JSONException {
        final JSONObject req = new JSONObject();
        final JSONObject data = new JSONObject();

        //data.put(Names.UID, 4);
        data.put(Names.Date, "2020-07-24");
        data.put(Names.Amount, 1);
        data.put(Names.TName, "test1");
        data.put(Names.Price, 50);
        data.put(Names.isIncome, false);
        data.put(Names.Category, "Category test");
        data.put(Names.Currency, "NIS");
        data.put(Names.Description, "this is my Description");
        data.put(Names.PaymentType, "Cash");
        data.put(Names.isRepeat, false);
        data.put(Names.Repeat, 0);

        req.put("cmd", "insert");
        req.put("table", Names.Transactions_Table);
        req.put("data", data.toString());

        //JSONObject result = this.handler.handleRequest(req.toString());
        //System.out.println(result.getBoolean("data"));
        this.service.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    handler.handleRequest(req.toString(), 4);
                }catch (ProductsException e){
                    //TODO catch
                }
            }
        });
        final JSONObject response = handler.getResponse();
        System.out.println("sending data: " + data);
        view.post(new Runnable() {
            @Override
            public void run() {
                try {
                    view.evaluateJavascript("handleResponse('" + response.getString(Names.Data) + "')",
                            null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @android.webkit.JavascriptInterface
    public void loginTest(){
        this.service.submit(new Runnable() {
            @Override
            public void run() {
                try {

                    String info = "{'columns': 'id', 'whereClause': 'Email = ? AND Password = ?'," +
                            "'whereArgs': 'test@gmail.com,Test1234'}";
                    String req = "{'cmd': 'get', 'table': 'Profile', 'data':'" + info +"'}";

                    String x = handler.handleRequest(req, 1);
                    System.out.println(x);
                } catch (ProductsException e) {
                    e.printStackTrace();
                }
            }});
    }
*/
}

package com.aquery;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.aquery.query.QueryDate;
import com.aquery.query.QueryNetwork;
import com.aquery.query.QuerySqlite;
import com.aquery.query.QueryView;
import com.aquery.utils.Loader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ocittwo on 11/11/17.
 */

public class AQuery {

    private Context context;
    private View rootView;

    private QueryView queryView;
    private QueryNetwork queryNetwork;

    private AlertDialog.Builder alert;
    private Loader loader;
    private SharedPreferences pref;
    private Gson gson;

    public AQuery(Context context) {
        this.context = context;
        rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        initialize(context);
    }

    public AQuery(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
        initialize(context);
    }
    
    private initialize(Context context) {
        queryView = new QueryView(context);
        queryNetwork = new QueryNetwork(context);
        alert = new AlertDialog.Builder(context);
        loader = new Loader(context);
        pref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
    }

    public long now(){
        return System.currentTimeMillis();
    }

    public QueryView id(@IdRes int id) {
        return queryView.setView(rootView.findViewById(id));
    }

    public String text(@IdRes int id) {
        return id(id).text();
    }

    public boolean isValid(@IdRes int id) {
        return id(id).isValid();
    }

    public QueryNetwork ajax(String url) {
        return queryNetwork.setUrl(url);
    }

    public void alert(String messages) {
        alert.setTitle("Message");
        alert.setMessage(messages);
        alert.setPositiveButton("Done", (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    public void hideKeyboard() {
        Activity activity = (Activity) context;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void toast(String messages) {
        Toast.makeText(context, messages, Toast.LENGTH_SHORT).show();
    }

    public void snack(String message){
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    public void runOnMainThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public void showPopupLoading() {
        loader.hide();
    }

    public void hidePopupLoading() {
        loader.hide();
    }

    public void saveString(String key, String value) {
        pref.edit().putString(key, value).apply();
    }

    public String grabString(String key) {
        return pref.getString(key, null);
    }

    public void open(Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    public void open(Intent intent) {
        context.startActivity(intent);
    }

    public void openFromRight(Class clazz) {
        open(clazz);
        ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void openFromLeft(Class clazz) {
        open(clazz);
        ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void openFromRight(Intent intent) {
        open(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void openFromLeft(Intent intent) {
        open(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void closeToRight() {
        Activity activity = (Activity) this.context;
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void closeToLeft() {
        Activity activity = (Activity) this.context;
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public <T> T toObject(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public QueryView view(View itemView) {
        return queryView.setView(itemView);
    }

    public int getIntIntent(String key) {
        Activity activity = (Activity) this.context;
        return activity.getIntent().getIntExtra(key, 0);
    }

    public String getStringIntent(String key) {
        Activity activity = (Activity) this.context;
        return activity.getIntent().getStringExtra(key);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void setBackIndicator() {
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        }
    }

    public QuerySqlite sql() {
        return new QuerySqlite(context);
    }

    public QueryDate date() {
        return new QueryDate(now());
    }

    public void clearPref() {
        pref.edit().clear().apply();
    }
}

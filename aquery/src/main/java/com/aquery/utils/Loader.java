package com.aquery.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ocittwo on 11/11/17.
 */

public class Loader {

    private ProgressDialog mProgressDialog;

    public Loader(Context context){
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading...");
    }

    /**
     * Show progress dialog
     */
    public void show() {
        mProgressDialog.show();
    }

    /**
     * Hide progress dialog
     */
    public void hide() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}

package com.aquery.query;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by ocittwo on 11/11/17.
 */

public class QueryView {

    private View view;
    private Context context;

    public QueryView(Context context) {
        this.context = context;
    }

    public QueryView setView(View view) {
        this.view = view;
        return this;
    }

    public void text(String text) {
        if (view instanceof TextView){
            TextView textView = (TextView) view;
            textView.setText(text);
        }else if (view instanceof EditText){
            EditText editText = (EditText) view;
            editText.setText(text);
        }else if (view instanceof AppCompatEditText){
            AppCompatEditText appCompatEditText = (AppCompatEditText) view;
            appCompatEditText.setText(text);
        }
    }

    public String text() {
        if (view instanceof TextView){
            TextView textView = (TextView) view;
            return textView.getText().toString();
        }else if (view instanceof EditText){
            EditText editText = (EditText) view;
            return editText.getText().toString();
        }else if (view instanceof AppCompatEditText){
            AppCompatEditText appCompatEditText = (AppCompatEditText) view;
            return appCompatEditText.getText().toString();
        }else {
            return "";
        }
    }

    public void image(Object source){
        if (view instanceof ImageView){
            ImageView imageView = (ImageView) view;
            Glide.with(context)
                    .load(source)
                    .into(imageView);
        }
    }

    public void click(View.OnClickListener clickListener){
        view.setOnClickListener(clickListener);
    }

    public boolean isValid(){
        if (view instanceof EditText){
            EditText editText = (EditText) view;
            if (TextUtils.isEmpty(editText.getText().toString())){
                editText.setError("Required");
                return false;
            }else {
                return true;
            }
        }else{
            return false;
        }
    }
}

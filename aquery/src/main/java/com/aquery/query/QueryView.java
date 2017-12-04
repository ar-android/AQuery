package com.aquery.query;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.aquery.utils.ImageRounded;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * Created by ocittwo on 11/11/17.
 */

public class QueryView {

    private RequestManager requestManager;
    private View view;
    private Context context;
    private Object source;
    private LinearLayoutManager linearLayoutManager;

    public QueryView(Context context) {
        this.context = context;
        linearLayoutManager = new LinearLayoutManager(context);
        try {
            requestManager = Glide.with(context);
        } catch (Exception e) {

        }
    }

    public QueryView setView(View view) {
        this.view = view;
        return this;
    }

    public QueryView text(String text) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(text);
        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;
            editText.setText(text);
        } else if (view instanceof AppCompatEditText) {
            AppCompatEditText appCompatEditText = (AppCompatEditText) view;
            appCompatEditText.setText(text);
        } else if (view instanceof RadioButton) {
            RadioButton radioButton = (RadioButton) view;
            radioButton.setText(text);
        }
        return this;
    }

    public String text() {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            return textView.getText().toString();
        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;
            return editText.getText().toString();
        } else if (view instanceof AppCompatEditText) {
            AppCompatEditText appCompatEditText = (AppCompatEditText) view;
            return appCompatEditText.getText().toString();
        } else {
            return "";
        }
    }

    public QueryView image(Object source) {
        this.source = source;
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            requestManager.load(source).into(imageView);
        }
        return this;
    }

    public void click(View.OnClickListener clickListener) {
        view.setOnClickListener(clickListener);
    }

    public boolean isValid() {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            if (TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError("Required");
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public void rounded() {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            requestManager.clear(imageView);
            requestManager.asBitmap()
                    .load(source)
                    .into(new ImageRounded(imageView));
        }
    }

    public RecyclerView recyclerView() {
        return ((RecyclerView) view);
    }

    public QueryView adapter(RecyclerView.Adapter adapter) {
        ((RecyclerView) view).setLayoutManager(linearLayoutManager);
        ((RecyclerView) view).setAdapter(adapter);
        return this;
    }

    public void grid(int spanCount) {
        ((RecyclerView) view).setLayoutManager(new GridLayoutManager(context, spanCount));
    }

    public void centerCrop() {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    public void hide() {
        view.setVisibility(View.GONE);
    }

    public void show() {
        view.setVisibility(View.VISIBLE);
    }

    public <T> T as(Class<T> viewClass) {
        return (T) view;
    }

    public void active() {
        view.setActivated(true);
    }

    public void inActive() {
        view.setActivated(false);
    }

    public QueryView id(@IdRes int id) {
        View viewById = view.findViewById(id);
        return setView(viewById);
    }

    public void disable() {
        view.setEnabled(false);
    }

    public QueryView enable() {
        view.setEnabled(true);
        return this;
    }

    public LinearLayoutManager getLinearLayoutManager() {
        return linearLayoutManager;
    }

    public void staggeredGrid(int rowSpan) {
        as(RecyclerView.class)
                .setLayoutManager(new StaggeredGridLayoutManager(rowSpan, StaggeredGridLayoutManager.VERTICAL));
    }

    public int integer() {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            return Integer.parseInt(textView.getText().toString());
        } else {
            return 0;
        }
    }
}

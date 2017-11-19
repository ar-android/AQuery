package com.aquery.query;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.aquery.BuildConfig;
import com.aquery.listener.QueryNetworkByteListener;
import com.aquery.listener.QueryNetworkListener;
import com.aquery.listener.QueryNetworkObjectListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

/**
 * Created by ocittwo on 11/11/17.
 */

public class QueryNetwork {

    private Context context;
    private String url;
    private OkHttpClient client;
    private Request request;
    private Gson gson;
    private ProgressDialog mProgressDialog;
    private boolean showLoading = false;

    public QueryNetwork(Context context) {
        this.context = context.getApplicationContext();
        client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BASIC)
                        .log(Platform.INFO)
                        .request("Request")
                        .response("Response")
                        .addHeader("version", BuildConfig.VERSION_NAME)
                        .build())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        gson = new GsonBuilder().serializeNulls().create();

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading...");
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public QueryNetwork setUrl(String url) {
        this.url = url;
        return this;
    }

    public QueryNetwork get() {
        request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Accept", "application/json")
                .build();
        return this;
    }

    public QueryNetwork get(Map<String, String> params) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                httpBuider.addQueryParameter(param.getKey(), param.getValue());
            }
        }
        request = new Request.Builder().url(httpBuider.build()).get().build();
        return this;
    }

    public void get(QueryNetworkListener listener) {
        request = new Request.Builder()
                .url(url)
                .get()
                .build();
        response(listener);
    }

    public void response(final QueryNetworkListener listener) {
        isShowLoading();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        runOnMainThread(() -> {
                            isHideLoading();
                            listener.response("", e);
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String body = response.body().string();
                        runOnMainThread(() -> {
                            isHideLoading();
                            listener.response(body, null);
                        });
                    }
                });
    }

    private void isShowLoading() {
        if (showLoading) {
            mProgressDialog.show();
        }
    }

    private void isHideLoading() {
        if (showLoading) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    private void runOnMainThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    private void runOnBackground(Runnable runnable) {
        new Thread(runnable).start();
    }

    private <T> T toObject(String json, Class<T> classOfT) {
        try {
            return gson.fromJson(json, classOfT);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> void toObject(final Class<T> clazz, final QueryNetworkObjectListener<T> listener) {
        isShowLoading();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        runOnMainThread(() -> {
                            isHideLoading();
                            listener.response(null, e);
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String body = response.body().string();
                        try {
                            T object = toObject(body, clazz);
                            runOnMainThread(() -> {
                                isHideLoading();
                                if (object == null)
                                    listener.response(null, new Exception(body));
                                else
                                    listener.response(object, null);
                            });
                        } catch (Exception e) {
                            runOnMainThread(() -> {
                                isHideLoading();
                                listener.response(null, e);
                            });
                        }
                    }
                });
    }

    public QueryNetwork showLoading() {
        showLoading = true;
        return this;
    }

    public void toByte(final QueryNetworkByteListener listener) {
        isShowLoading();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        runOnMainThread(() -> {
                            isHideLoading();
                            listener.response(null, e);
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final byte[] bytes = response.body().bytes();
                        runOnMainThread(() -> {
                            isHideLoading();
                            listener.response(bytes, null);
                        });
                    }
                });
    }

    String urlEncode(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUtf8(entry.getKey().toString()),
                    urlEncodeUtf8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

    String urlEncodeUtf8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public QueryNetwork postUrlEncoded(Map<String, String> params) {
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), urlEncode(params));
        request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        return this;
    }

    public QueryNetwork post(Map<String, String> params) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(params));
        request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        return this;
    }

    public QueryNetwork addHeader(String key, String value) {
        if (request != null) {
            request = request.newBuilder()
                    .addHeader(key, value)
                    .addHeader("Accept", "application/json")
                    .build();
        }
        return this;
    }
    
    public QueryNetwork addHeaders(Map<String, String> params) {
        if (request != null) {
            request = request.newBuilder()
                    .addHeaders(params)
                    .addHeader("Accept", "application/json")
                    .build();
        }
        return this;
    }

    public QueryNetwork postForm(Map<String, String> params) {
        MultipartBody.Builder form = new MultipartBody.Builder();
        form.setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            form.addFormDataPart(entry.getKey(), entry.getValue());
        }
        MultipartBody body = form.build();
        postMultipartBody(body);
        return this;
    }

    public QueryNetwork postMultipartBody(MultipartBody body) {
        request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .build();
        return this;
    }
}

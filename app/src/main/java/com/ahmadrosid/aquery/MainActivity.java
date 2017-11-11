package com.ahmadrosid.aquery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aquery.AQuery;
import com.aquery.listener.QueryNetworkListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aq = new AQuery(this);

        aq.id(R.id.image).image(R.drawable.rosid);
        aq.id(R.id.text).text("Ahmad Rosid");

//        aq.id(R.id.image).image("https://goo.gl/Yfue18");

        aq.id(R.id.click_login).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Map<String, String> params = new HashMap<>();
        params.put("nama", "Ahmad Rosid");
        aq.ajax("https://ocit-tutorial.herokuapp.com/index.php")
                .post(params)
                .showLoading()
                .response(new QueryNetworkListener() {
                    @Override
                    public void response(String response, Throwable error) {
                        if (response != null){
                            aq.alert(response);
                        }
                    }
                });

//        aq.ajax("https://api.github.com/users/ar-android")
//                .get()
//                .showLoading()
//                .toByte(new QueryNetworkByteListener() {
//                    @Override
//                    public void response(byte[] bytes, Throwable throwable) {
//                        int length = bytes.length;
//                        aq.alert(String.valueOf(length));
//                    }
//                });

//        aq.ajax("https://api.github.com/users/ar-android")
//                .get()
//                .showLoading()
//                .toObject(GithubUsers.class, new QueryNetworkObjectListener<GithubUsers>() {
//                    @Override
//                    public void response(GithubUsers response, Throwable error) {
//                        if (error != null){
//                            aq.alert(error.getMessage());
//                        }else{
//                            aq.alert(response.getName());
//                        }
//                    }
//                });

    }
}

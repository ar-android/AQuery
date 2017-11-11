package com.ahmadrosid.aquery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aquery.AQuery;
import com.aquery.listener.QueryNetworkListener;
import com.aquery.listener.QueryNetworkObjectListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aq = new AQuery(this);

        aq.ajax("https://api.github.com/users/ar-android")
                .get()
                .showLoading()
                .toObject(GithubUsers.class, (user, error) -> {
                    if (error != null){
                        aq.alert(error.getMessage());
                    }else{
                        aq.id(R.id.image).image(user.getAvatar_url()).rounded();
                        aq.id(R.id.name).text(user.getName());
                        aq.id(R.id.location).text(user.getLocation());
                    }
                });

        aq.id(R.id.click_logout).click(v -> aq.closeToLeft());

    }
}

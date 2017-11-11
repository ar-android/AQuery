package com.ahmadrosid.aquery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aquery.AQuery;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        aq = new AQuery(this);
        setTitle("Login");

        aq.id(R.id.login).click(v -> {
            aq.hideKeyboard();
            if (aq.id(R.id.email).isValid() && aq.id(R.id.password).isValid()){
                Map<String, String> params = new HashMap<>();
                params.put("email", aq.id(R.id.email).text());
                params.put("password", aq.id(R.id.password).text());

                aq.saveString("email", aq.id(R.id.email).text());
                aq.saveString("password", aq.id(R.id.password).text());

                aq.ajax("https://ocit-tutorial.herokuapp.com/index.php")
                        .post(params)
                        .showLoading()
                        .response((response, error) -> {
                            if (response != null){
                                aq.openFromRight(MainActivity.class);
                            }
                        });
            }
        });

    }
}

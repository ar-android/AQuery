package com.ahmadrosid.aquery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aquery.AQuery;

public class LoginActivity extends AppCompatActivity {

    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        aq = new AQuery(this);
        setTitle("Login");

        aq.id(R.id.login).click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aq.id(R.id.email).isValid() && aq.id(R.id.password).isValid()){
                    aq.alert("Valid input.");
                }
            }
        });
    }
}

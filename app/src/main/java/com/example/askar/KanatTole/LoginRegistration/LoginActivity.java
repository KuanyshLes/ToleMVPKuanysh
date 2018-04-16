package com.example.askar.KanatTole.LoginRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.askar.KanatTole.MainActivity;
import com.example.askar.task3bottomnavigationview.R;



public class LoginActivity extends AppCompatActivity {
    Button buttonCreate, buttonLogin;

    @Override
    public void onBackPressed() {
        finish();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonCreate = (Button) findViewById(R.id.buttonCreateAccount);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationChoiceActivity.class);
                startActivity(intent);
                finish();

            }
        });

        TextView tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword1Activity.class);
                startActivityForResult(intent, 1337);
                finish();

            }
        });



    }


}

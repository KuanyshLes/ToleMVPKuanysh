package com.example.askar.KanatTole.LoginRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.askar.task3bottomnavigationview.R;

/**
 * Created by askar on 30.03.2018.
 */

public class ForgotPassword1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password1);


        TextView tvBack = (TextView) findViewById(R.id.buttonBack);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgotPassword1Activity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword2Activity.class);
                startActivityForResult(intent, 1338);
                finish();
            }
        });



    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ForgotPassword1Activity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

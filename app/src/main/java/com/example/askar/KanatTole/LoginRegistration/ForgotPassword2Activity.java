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

public class ForgotPassword2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2);



        TextView tvBack = (TextView) findViewById(R.id.buttonBack);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgotPassword2Activity.this,ForgotPassword1Activity.class);
                startActivity(intent);
                finish();
            }
        });
        Button buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Forgot_Password3Activity.class);
                startActivity(intent);
                finish();

            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ForgotPassword2Activity.this,ForgotPassword1Activity.class);
        startActivity(intent);
        finish();
    }
}

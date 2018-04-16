package com.example.askar.KanatTole.LoginRegistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.askar.KanatTole.MainActivity;
import com.example.askar.task3bottomnavigationview.R;

public class Forgot_Password3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password3);
        TextView tvBack = (TextView) findViewById(R.id.buttonBack);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Forgot_Password3Activity.this,ForgotPassword2Activity.class);
                startActivity(intent);
                finish();
            }
        });
        Button buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 1338);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Forgot_Password3Activity.this,ForgotPassword2Activity.class);
        startActivity(intent);
        finish();
    }
}

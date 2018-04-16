package com.example.askar.KanatTole.LoginRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.askar.task3bottomnavigationview.R;



public class RegistrationChoiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_choice);

        TextView buttonBack;
        buttonBack=findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(RegistrationChoiceActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ImageView owner = (ImageView) findViewById(R.id.imageViewOwner);
        ImageView worker = (ImageView) findViewById(R.id.imageViewWorker);

        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OwnerRegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EmployeesRegistration.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(RegistrationChoiceActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

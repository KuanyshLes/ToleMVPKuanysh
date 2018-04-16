package com.example.askar.KanatTole;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.askar.KanatTole.LoginRegistration.LoginActivity;
import com.example.askar.task3bottomnavigationview.R;

public class Welcome extends AppCompatActivity {
    int n=0;
    SharedPreferences sharedPreferences;
    final String welcomeScreenShownPref = "welcomeScreenShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean welcomeScreenShown = sharedPreferences.getBoolean(welcomeScreenShownPref, false);
        if(!welcomeScreenShown){
            final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            SimpleFragmentPagerAdapter  adapter = new SimpleFragmentPagerAdapter(this,getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            viewPager.beginFakeDrag();
            Button next=(Button) findViewById(R.id.next_button);
            next.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    n++;
                    if(n<3){
                        viewPager.setCurrentItem(n);
                    }
                    if(n>2){
                        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(welcomeScreenShownPref, true);
                        editor.commit();
                        finish();
                    }

                }
            });

        }
        if(welcomeScreenShown){
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }


}

package com.example.askar.KanatTole.LoginRegistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.askar.KanatTole.API.Services.API_client;
import com.example.askar.KanatTole.API.Services.API_routes;
import com.example.askar.KanatTole.MainActivity;
import com.example.askar.task3bottomnavigationview.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by askar on 30.03.2018.
 */

public class OwnerRegistrationActivity extends AppCompatActivity {
    EditText name,surname,nameOf,email,phone,pass,conpass;
    CheckBox checkBox;
    Button buttonLogin;
    private JsonObject jsonMessageSuccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name=findViewById(R.id. name_edit);
        surname=findViewById(R.id.surname_edit);
        email=findViewById(R.id.email_edit);
        phone=findViewById(R.id.phone_number_edit);
        nameOf=findViewById(R.id.name_of_sell_edit);
        pass=findViewById(R.id.pass_edit);
        conpass=findViewById(R.id.confirm_pass_edit);
        checkBox=findViewById(R.id.checkBox);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        TextView buttonBack=findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OwnerRegistrationActivity.this,RegistrationChoiceActivity.class);
                startActivity(intent);
                finish();
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(!checkBox.isChecked()){
                    nameOf.setError(null);
                    name.setError(null);
                    surname.setError(null);
                    email.setError(null);
                    phone.setError(null);
                    pass.setError(null);
                    conpass.setError(null);

                }

            }
        });
        phone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(phone.getText().toString().isEmpty()){
                    phone.append("+7");
                }
                return false;
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                    boolean tr=true;
                    if(!checkBox.isChecked()){
                        RelativeLayout relativeLayout=findViewById(R.id.activity_own);
                        Snackbar snackbar=Snackbar.make(relativeLayout,"Сначала отметьте галочкой",Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    else{
                        if(!nameOf.getText().toString().matches("[a-zA-Z_0-9_А-ЯЁа-яё]{1,20}")){
                            nameOf.setError("Не прввильный формат");
                            nameOf.requestFocus();
                            tr=false;
                        }
                        if(!name.getText().toString().matches("(([А-ЯЁ][а-яё]+[\\-\\s]?){0,1})|(([A-Z][a-z]+[\\-\\s]?){0,1})")||name.getText().toString().length()>15||name.getText().toString().isEmpty()){
                            name.setError("Неверный формат");
                            name.requestFocus();
                            tr=false;
                        }
                        if(!surname.getText().toString().matches("(([А-ЯЁ][а-яё]+[\\-\\s]?){0,1})|(([A-Z][a-z]+[\\-\\s]?){0,1})")||surname.getText().toString().length()>15||name.getText().toString().isEmpty()){
                            surname.setError("Неверный формат");
                            surname.requestFocus();
                            tr=false;
                        }
                        if(!phone.getText().toString().matches("\\+?\\d+([\\(\\s\\-]?\\d+[\\)\\s\\-]?[\\d\\s\\-]+)?")||phone.getText().toString().length()>15){
                            phone.setError("Неверный формат");
                            phone.requestFocus();
                            tr=false;
                        }
                        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                            email.setError("Неверный формат");
                            email.requestFocus();
                            tr=false;
                        }
                        if(!pass.getText().toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")){
                            pass.setError("Неверный формат");
                            pass.requestFocus();
                            tr=false;
                        }
                        if(!conpass.getText().toString().equals(pass.getText().toString())||conpass.getText().toString().isEmpty()){
                            conpass.setError("Неверный формат");
                            conpass.requestFocus();
                            tr=false;
                        }
                        if(tr) {
                            nameOf.setError(null);
                            name.setError(null);
                            surname.setError(null);
                            email.setError(null);
                            phone.setError(null);
                            pass.setError(null);
                            conpass.setError(null);

                            final JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("firstname",name.getText().toString());
                            jsonObject.addProperty("lastname",surname.getText().toString());
                            jsonObject.addProperty("companyName",nameOf.getText().toString());
                            jsonObject.addProperty("phone",phone.getText().toString().substring(1));
                            jsonObject.addProperty("email",email.getText().toString());
                            jsonObject.addProperty("password",pass.getText().toString());
                            jsonObject.addProperty("rePassword",conpass.getText().toString());
                            API_routes api = API_client.getClient().create(API_routes.class);
                            //Registration
                            Call<JsonObject> call = api.ownerRegistration(jsonObject);
                            if (isNetworkConnected()){
                                call.enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        Log.i("","Response :"+response.body());

                                        if(response.body()!=null){
                                            String messageSuccess = response.body().get("message").toString();
                                            messageSuccess=messageSuccess.substring(messageSuccess.length()-3);
                                            messageSuccess=messageSuccess.replace("\"","");
                                            AccountData accountData = new AccountData(messageSuccess,name.getText().toString(), surname.getText().toString(), email.getText().toString(),nameOf.getText().toString(), phone.getText().toString(), pass.getText().toString());
                                            saveCurrentAccountData(getApplicationContext(), accountData);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(OwnerRegistrationActivity.this,"Регистрация успешно!",Toast.LENGTH_SHORT).show();

                                            finish();
                                        }
                                        else {
                                            Toast.makeText(OwnerRegistrationActivity.this,"Result is null",Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                        Log.d("myTag","Error :\n"+ t.getMessage());
                                    }
                                });
                            }

                        }
                    }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(OwnerRegistrationActivity.this,RegistrationChoiceActivity.class);
        startActivity(intent);
        finish();
    }
    public static void saveCurrentAccountData(Context context, AccountData accountData){
        SharedPreferences sharedPreferences=context.getSharedPreferences("CurrentAccountData",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();

        Gson gson=new Gson();
        String json=gson.toJson(accountData);
        editor.putString("",json);
        editor.commit();

    }
    public static AccountData getCurrentAccountData(Context context){
        SharedPreferences  sharedPreferences=context.getSharedPreferences("CurrentAccountData",context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("","");
        if(json.equalsIgnoreCase("")){
            return null;
        }
        AccountData accountData=gson.fromJson(json,AccountData.class);
        return accountData;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }



}

package com.example.askar.KanatTole.LoginRegistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.askar.KanatTole.MainActivity;
import com.example.askar.task3bottomnavigationview.R;
import com.google.gson.Gson;

public class EmployeesRegistration extends AppCompatActivity {
    EditText name,surname,phone,entercode,pass,conpass;
    CheckBox checkBox;
    Button buttonLogin;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_registration);
        name=findViewById(R.id. name_edit);
        surname=findViewById(R.id.surname_edit);
        phone=findViewById(R.id.phone_number_edit);
        entercode=findViewById(R.id.code_enter_edit);
        pass=findViewById(R.id.pass_edit);
        conpass=findViewById(R.id.confirm_pass_edit);
        checkBox=findViewById(R.id.checkBox);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        TextView buttonBack=findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmployeesRegistration.this,RegistrationChoiceActivity.class);
                startActivity(intent);
                finish();
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox.isChecked()){
                    buttonLogin.setEnabled(true);
                }
                else if(!checkBox.isChecked()){
                    entercode.setError(null);
                    name.setError(null);
                    surname.setError(null);
                    phone.setError(null);
                    pass.setError(null);
                    conpass.setError(null);
                    buttonLogin.setEnabled(false);
                }

            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                boolean tr=true;

                if(!entercode.getText().toString().matches("[0-9]{1,8}")){
                    entercode.setError("Не прввильный формат");
                    entercode.requestFocus();
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
                    entercode.setError(null);
                    name.setError(null);
                    surname.setError(null);
                    phone.setError(null);
                    pass.setError(null);
                    conpass.setError(null);
                    EmployeeData accountData = new EmployeeData(name.getText().toString(), surname.getText().toString(), entercode.getText().toString(), phone.getText().toString(), pass.getText().toString());
                    setEmploeeData(getApplicationContext(), phone.getText().toString(), accountData);
                    Toast.makeText(getApplicationContext(), getData(getApplicationContext(), phone.getText().toString()).toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(EmployeesRegistration.this,RegistrationChoiceActivity.class);
        startActivity(intent);
        finish();
    }
    public static void setEmploeeData(Context context, String title, EmployeeData employeeData){
        SharedPreferences sharedPreferences=context.getSharedPreferences("EmployeesDatas",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(employeeData);
        editor.putString(title,json);
        editor.commit();

    }
    public static EmployeeData getData(Context context, String title){
        SharedPreferences  sharedPreferences=context.getSharedPreferences("EmployeesDatas",context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString(title,"");
        if(json.equalsIgnoreCase("")){
            return null;
        }
        EmployeeData employeeData=gson.fromJson(json,EmployeeData.class);
        return employeeData;
    }
}

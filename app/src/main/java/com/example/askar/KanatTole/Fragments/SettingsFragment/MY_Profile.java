package com.example.askar.KanatTole.Fragments.SettingsFragment;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.askar.task3bottomnavigationview.R;
import com.google.gson.Gson;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MY_Profile extends Fragment {
    Button saving;
    TextView edit;
    TextView cancel;
    EditText edit_fio,edit_org,edit_phone,edit_pass;
    String re1,re2,re4,re5;

    public MY_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my__profile, container, false);
        saving=v.findViewById(R.id.saving_button);
        edit=v.findViewById(R.id.edit);
        cancel=v.findViewById(R.id.cancel);

        edit_fio=v.findViewById(R.id.edit_fio);
        edit_org=v.findViewById(R.id.edit_org);
        edit_phone=v.findViewById(R.id.edit_phone);
        edit_pass=v.findViewById(R.id.edit_pass);
        showAllData();
        re1=edit_fio.getText().toString();
        re2=edit_org.getText().toString();

        re4=edit_phone.getText().toString();
        re5=edit_pass.getText().toString();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit.getText().toString().equals("Изменить")){
                    cancel.setVisibility(View.INVISIBLE);
                    edit.setText("Отменить");
                    edit.setTextColor(Color.parseColor("#51cec1"));

                    edit_fio.setEnabled(true);

                    edit_org.setEnabled(true);

                    edit_phone.setEnabled(true);

                    edit_pass.setEnabled(true);

                    saving.setVisibility(View.VISIBLE);
                }
                else if(edit.getText().toString().equals("Отменить")){
                    cancel.setVisibility(View.VISIBLE);
                    saving.setVisibility(View.INVISIBLE);
                    edit.setText("Изменить");
                    edit_fio.setText(re1);
                    edit_org.setText(re2);
                    edit_phone.setText(re4);
                    edit_pass.setText(re5);
                    edit_fio.setEnabled(false);
                    edit_fio.setError(null);
                    edit_org.setEnabled(false);
                    edit_org.setError(null);
                    edit_phone.setEnabled(false);
                    edit_phone.setError(null);
                    edit_pass.setEnabled(false);
                    edit_pass.setError(null);
                    edit.setTextColor(Color.parseColor("#b2bcbb"));
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    loadFragment(new MainFragment());


            }
        });
        saving.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                boolean tr=true;
                if(!edit_fio.getText().toString().matches("(([А-ЯЁ][а-яё]+[\\-\\s]?){2,})|(([A-Z][a-z]+[\\-\\s]?){2,})")||edit_fio.getText().toString().isEmpty()){
                    edit_fio.setError("Неверный формат");
                    edit_fio.requestFocus();
                    tr=false;
                }
                if(!edit_org.getText().toString().matches("[a-zA-Z_0-9_А-ЯЁа-яё_\\s]{1,20}")){
                    edit_org.setError("Неверный формат");
                    edit_org.requestFocus();
                    tr=false;
                }
                if(!edit_phone.getText().toString().matches("\\+?\\d+([\\(\\s\\-]?\\d+[\\)\\s\\-]?[\\d\\s\\-]+)?")||edit_phone.getText().toString().length()>15){
                    edit_phone.setError("Неверный формат");
                    edit_phone.requestFocus();
                    tr=false;
                }
                if(!edit_pass.getText().toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")){
                    edit_pass.setError("Неверный формат");
                    edit_pass.requestFocus();
                    tr=false;
                }
                if(tr){
                    cancel.setVisibility(View.VISIBLE);
                    saving.setVisibility(View.INVISIBLE);
                    edit.setText("Изменить");
                    edit.setTextColor(Color.parseColor("#b2bcbb"));
                    re1=edit_fio.getText().toString();
                    re2=edit_org.getText().toString();
                    re4=edit_phone.getText().toString();
                    re5=edit_pass.getText().toString();

                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Interprice",getActivity().MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    Gson gson=new Gson();
                    editor.putString("Name",re1);
                    editor.putString("Org",re2);
                    editor.putString("Phone number",re4);
                    editor.putString("Password",re5);
                    editor.commit();

                    edit_fio.setText(re1);
                    edit_org.setText(re2);
                    edit_phone.setText(re4);
                    edit_pass.setText(re5);
                    edit_fio.setEnabled(false);
                    edit_org.setEnabled(false);
                    edit_phone.setEnabled(false);
                    edit_pass.setEnabled(false);
                }

            }
        });

        return v;
    }
    public Map<String,String> getAllEntries(SharedPreferences sharedPreferences){
        Map<String,String> allEntries = (Map<String, String>) sharedPreferences.getAll();
        return allEntries;
    }
    public void showAllData(){
        Map<String,String> map=getAllEntries(getActivity().getSharedPreferences("Interprice",getActivity().MODE_PRIVATE));
        edit_fio.setText(map.get("Name"));
        edit_org.setText(map.get("Org"));
        edit_phone.setText(map.get("Phone number"));
        edit_pass.setText(map.get("Password"));
    }
    private void loadFragment(Fragment fragment){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.container,fragment).addToBackStack(null);
        fragmentTransaction.commit();


    }

}

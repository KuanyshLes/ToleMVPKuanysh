package com.example.askar.KanatTole.Fragments.SettingsFragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.askar.KanatTole.LoginRegistration.LoginActivity;
import com.example.askar.task3bottomnavigationview.R;

import java.util.Map;


public class MainFragment extends Fragment {
    RelativeLayout rl_my_profile,rl_add_document,rl_support;
    LinearLayout rl_employers_list;
    Button exit_button;
    TextView textIndicator;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main, container, false);
        rl_my_profile=v.findViewById(R.id.rl_my_profile);
        rl_employers_list=v.findViewById(R.id.rl_employers_list);
        rl_support=v.findViewById(R.id.rl_support);
        exit_button=v.findViewById(R.id.exit_button);
        textIndicator=v.findViewById(R.id.text_amount_of_employers);
        if(sizeOfEmployersData(getActivity().getSharedPreferences("DataEmployers",getActivity().MODE_PRIVATE))!=0){
            textIndicator.setText (String.valueOf( sizeOfEmployersData(getActivity().getSharedPreferences("DataEmployers",getActivity().MODE_PRIVATE))));}

        rl_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new MY_Profile());
            }
        });

        rl_employers_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Employers());
            }
        });
        rl_support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loadFragment(new SupportFragment());
            }
        });
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent intent=new Intent(getActivity(),LoginActivity.class);
              startActivity(intent);
                getActivity().finish();
            }
        });


        return v;
    }
    private void loadFragment(Fragment fragment){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment).addToBackStack(null);
        fragmentTransaction.commit();


    }
    public int sizeOfEmployersData(SharedPreferences sharedPreferences){
        int size=getAllEntries(sharedPreferences).size();
        return size;
    }
    public Map<String,String> getAllEntries(SharedPreferences sharedPreferences){
        Map<String,String> allEntries = (Map<String, String>) sharedPreferences.getAll();
        return allEntries;
    }


}

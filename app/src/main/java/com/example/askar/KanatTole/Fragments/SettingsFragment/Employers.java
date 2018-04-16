package com.example.askar.KanatTole.Fragments.SettingsFragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.askar.KanatTole.Models.Employer;
import com.example.askar.task3bottomnavigationview.R;
import com.example.askar.KanatTole.Utils.adapters.EmployersAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;


public class Employers extends Fragment {
    Button adding_button;
    RecyclerView recyclerView;
    TextView cancel;

    public Employers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_employers, container, false);

        cancel=v.findViewById(R.id.cancel);
        adding_button=v.findViewById(R.id.adding_button);
        recyclerView=v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final ArrayList<Employer> employers =getAllEmployers();

        final EmployersAdapter employers_adapter=new EmployersAdapter(employers,getActivity(),cancel,recyclerView,adding_button,getActivity().getWindow());
        recyclerView.setAdapter(employers_adapter);

        adding_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                employers_adapter.addItem(new Employer(employers_adapter.getItemCount(),"","",false));
                adding_button.setEnabled(false);


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean tr=true;
                if(cancel.getCurrentTextColor()== Color.parseColor("#51cec1"))  {
                    loadFragment(new MainFragment());
                }
                else if(cancel.getCurrentTextColor()!=Color.parseColor("#51cec1")){
                    cancel.setTextColor(Color.parseColor("#51cec1"));
                    for(int j=0;j<recyclerView.getChildCount();j++){
                        recyclerView.getChildAt(j).findViewById(R.id.name_edit).setEnabled(false);
                        recyclerView.getChildAt(j).findViewById(R.id.edit_phone_number).setEnabled(false);
                    }
                }
            }
        });
        return v;
    }
    private void loadFragment(Fragment fragment){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }

    public    Map<String,String> getAllEntries(SharedPreferences sharedPreferences){
        Map<String,String> allEntries = (Map<String, String>) sharedPreferences.getAll();
        return allEntries;
    }
    public ArrayList<String> getAllPhones(){
        ArrayList<Employer> emps=getAllEmployers();
        ArrayList<String> phoneNumbers=new ArrayList<>();
        for(int i=0;i<emps.size();i++){
            phoneNumbers.add(emps.get(i).getPhone());
        }
        return phoneNumbers;
    }
    public ArrayList<Employer> getAllEmployers(){
        Map<String,String> map=getAllEntries(getActivity().getSharedPreferences("DataEmployers",getActivity().MODE_PRIVATE));
        ArrayList<String> strings=new ArrayList<>(map.values());
        ArrayList<Employer> emps=new ArrayList<>();
        Gson gson=new Gson();
        for(int i=0;i<strings.size();i++){
            emps.add(gson.fromJson(strings.get(i),Employer.class));
        }
        return emps;
    }


}

package com.example.askar.KanatTole.Fragments.SettingsFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.askar.task3bottomnavigationview.R;



public class SupportFragment extends Fragment {
    TextView cancel;

    public SupportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_support, container, false);
        cancel=v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new MainFragment());
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

}

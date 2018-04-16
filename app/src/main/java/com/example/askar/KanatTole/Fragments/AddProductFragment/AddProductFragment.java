package com.example.askar.KanatTole.Fragments.AddProductFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.askar.task3bottomnavigationview.R;

/**
 * Created by askar on 28.03.2018.
 */

public class AddProductFragment extends Fragment {
    private static final String TAG = "QrCodeFragment";

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_product, container, false);

        return view;
    }
}

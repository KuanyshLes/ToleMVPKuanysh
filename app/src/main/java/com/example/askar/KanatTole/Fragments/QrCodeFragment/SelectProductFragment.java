package com.example.askar.KanatTole.Fragments.QrCodeFragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.askar.KanatTole.Models.Products;
import com.example.askar.task3bottomnavigationview.R;
import com.example.askar.KanatTole.Utils.adapters.SelectProductAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SelectProductFragment extends Fragment implements View.OnClickListener, FragmentManager.OnBackStackChangedListener{

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    private  List<Products> list;

    private TextView addNewProduct,selectProduct;
    private Button saveBtn;

    private SharedPreferences sPref;

    private SelectProductAdapter mAdapter;

    public SelectProductFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_product, container, false);

        mToolbar = view.findViewById(R.id.select_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        loadProduct();

        addNewProduct = view.findViewById(R.id.addNewProductInSelectFragment);
        selectProduct = view.findViewById(R.id.selectCanselProduct);

        saveBtn = view.findViewById(R.id.saveProductBtn);
        saveBtn.setOnClickListener(this);

        addNewProduct.setOnClickListener(this);
        selectProduct.setOnClickListener(this);


        mRecyclerView = view.findViewById(R.id.selectProductRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new SelectProductAdapter(list,getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.saveProductBtn:
                saveProduct();
                transaction.replace(R.id.container,new CalculateProductFragment());
                transaction.commit();
                break;
            case R.id.selectCanselProduct:
                transaction.replace(R.id.container,new CalculateProductFragment());
                transaction.commit();
                break;
            case R.id.addNewProductInSelectFragment:
                transaction.replace(R.id.container,new QRScannerFragment());
                transaction.commit();
                break;
        }
    }


    public void saveProduct(){

        sPref = getActivity().getSharedPreferences("productsSharedRef",getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        Gson gson=new Gson();
        String json=gson.toJson(list);
        editor.putString("LIST",json);
        editor.apply();
    }
    public void loadProduct(){
        sPref=getActivity().getSharedPreferences("productsSharedRef",getActivity().MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sPref.getString("LIST",null);
        Type type=new TypeToken<ArrayList<Products>>(){}.getType();
        list=gson.fromJson(json,type);
    }

    @Override
    public void onBackStackChanged() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,new CalculateProductFragment());
        transaction.commit();
    }
}

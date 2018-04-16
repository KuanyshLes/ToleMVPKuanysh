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
import com.example.askar.KanatTole.Utils.adapters.CalculateProductAdapter;
import com.example.askar.KanatTole.Utils.adapters.SelectProductAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculateProductFragment extends Fragment implements View.OnClickListener {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    public List<Products> list;

    SelectProductAdapter selectProductAdapter;

    TextView addNewProduct,selectProduct;

    Button calculateSuccessBtn;

    private  SharedPreferences sPref;

    private CalculateProductAdapter mAdapter;

    public CalculateProductFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate_product, container, false);

        loadProduct();


        mToolbar = view.findViewById(R.id.calculate_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        calculateSuccessBtn = view.findViewById(R.id.calculateProductBtn);
        calculateSuccessBtn.setOnClickListener(this);



        addNewProduct = view.findViewById(R.id.addNewProduct);
        selectProduct = view.findViewById(R.id.selectProduct);

        addNewProduct.setOnClickListener(this);
        selectProduct.setOnClickListener(this);

        mRecyclerView = view.findViewById(R.id.calculateProductRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CalculateProductAdapter(getActivity(),list);
        mRecyclerView.setAdapter(mAdapter);

        return view;
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
    public void onPause(){
        super.onPause();
        saveProduct();
    }



    @Override
    public void onClick(View v) {
        saveProduct();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.addNewProduct:
                transaction.replace(R.id.container,new QRScannerFragment());
                transaction.commit();
                break;
            case R.id.selectProduct:

                transaction.replace(R.id.container,new SelectProductFragment());
                transaction.addToBackStack("calculate");
                transaction.commit();
                break;
            case R.id.calculateProductBtn:
                transaction.replace(R.id.container,new TotalProductFragment());
                transaction.addToBackStack("calculate");
                transaction.commit();
                break;

        }
    }


}

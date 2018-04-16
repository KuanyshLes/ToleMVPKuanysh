package com.example.askar.KanatTole.Fragments.QrCodeFragment;


import android.app.Dialog;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.askar.KanatTole.API.Services.API_client;
import com.example.askar.KanatTole.API.Services.API_routes;
import com.example.askar.task3bottomnavigationview.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class AddProductFragment extends Fragment implements View.OnClickListener , FragmentManager.OnBackStackChangedListener{

    public AddProductFragment() {}

    private String strTitle,strBarcode,purchaseCost,retailCost,strAmount;

    private Button btn;

    private TextView canselBtnInAddProduct;

    private EditText addProductName;
    private EditText purchasePrice;
    private EditText retailPrice;
    private EditText amountEditText;
    private EditText amountEditTextForNotification;

    private String qr_code_result = "";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        qr_code_result = getArguments().getString("QR_CODE_RESULT");

        addProductName = view.findViewById(R.id.addProductName);
        purchasePrice= view.findViewById(R.id.purchasePrice);
        retailPrice =  view.findViewById(R.id.retailPrice);
        amountEditText = view.findViewById(R.id.amountEditText);
        amountEditTextForNotification = view.findViewById(R.id.amountEditTextForNotification);

        canselBtnInAddProduct = view.findViewById(R.id.canselBtnInAddProduct);
        canselBtnInAddProduct.setOnClickListener(this);

        btn = view.findViewById(R.id.btnAddProductSuccess);
        btn.setOnClickListener(this);



        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddProductSuccess:
                onClickAddItem();
                break;

            case R.id.canselBtnInAddProduct:
                Bundle bundle = new Bundle();
                QRScannerFragment fragment = new QRScannerFragment();
                bundle.putBoolean("BOOL",false);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
                break;
        }
    }

    private void showDialog() {
        final Dialog dialogSilencer = new Dialog(getActivity());
        dialogSilencer.setCancelable(true);
        dialogSilencer.setContentView(R.layout.total_successing_dialog);
        TextView textSuccessing = dialogSilencer.findViewById(R.id.totalSuccessText);
        textSuccessing.setText("Успешно добавлено!");
        Button totalSuccess = dialogSilencer.findViewById(R.id.total_successing_new_add_product);
        totalSuccess.setText("Ок");

        totalSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSilencer.cancel();
                Bundle bundle = new Bundle();
                QRScannerFragment fragment = new QRScannerFragment();
                bundle.putBoolean("BOOL",false);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();

            }
        });
        dialogSilencer.show();
    }


    @Override
    public void onBackStackChanged() {
        Bundle bundle = new Bundle();
        QRScannerFragment fragment = new QRScannerFragment();
        bundle.putBoolean("BOOL",false);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
    public void onClickAddItem() {

        strTitle = addProductName.getText().toString();
        strBarcode = qr_code_result;
        purchaseCost = purchasePrice.getText().toString();
        retailCost = retailPrice.getText().toString();
        strAmount = amountEditText.getText().toString();

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("title",strTitle);
        jsonObject.addProperty("barcode",strBarcode);
        jsonObject.addProperty("purchaseCost",purchaseCost);
        jsonObject.addProperty("retailCost",retailCost);
        jsonObject.addProperty("amount",strAmount);

        API_routes api = API_client.getClient().create(API_routes.class);
        Call<JsonObject> call = api.addItemToCompany(jsonObject);
        if (isNetworkConnected()){
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    Log.d("myTag","Response :\n"+response.body());
                    showDialog();
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    Log.d("myTag","Error :\n"+ t.getMessage());
                }
            });
        }else{
            Toast.makeText(getActivity(), "У вас отсутствует интернет соединение", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}

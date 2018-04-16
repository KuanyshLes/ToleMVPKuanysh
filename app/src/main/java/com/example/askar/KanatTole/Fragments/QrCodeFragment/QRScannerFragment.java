package com.example.askar.KanatTole.Fragments.QrCodeFragment;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.askar.KanatTole.Models.Products;
import com.example.askar.task3bottomnavigationview.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.Result;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;


public class QRScannerFragment extends Fragment implements ZXingScannerView.ResultHandler{


    private View view;
    private Button mSellProductBtn;
    private ZXingScannerView scannerView;

    private TextView titleBtnInQRScanner;
    //kana chort
    public List<Products> list;
    SharedPreferences sPref;

    private boolean mBoolean;
    private static final int REQUEST_CAMERA = 1;
    public QRScannerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_qrscanner, container, false);

        loadProduct();

        titleBtnInQRScanner = view.findViewById(R.id.qrScannerTitle);
        mSellProductBtn = view.findViewById(R.id.sell_qr_product_btn);

        Bundle bundle = getArguments();
        if(bundle!=null) {
            mBoolean = bundle.getBoolean("BOOL");
            if (mBoolean) {
                mSellProductBtn.setVisibility(View.VISIBLE);
                titleBtnInQRScanner.setText("Добавить товар");
            } else {
                titleBtnInQRScanner.setText("Продажа товара");
                mSellProductBtn.setVisibility(View.GONE);
            }
        }
        else{
            mBoolean=true;
            mSellProductBtn.setVisibility(View.VISIBLE);
            titleBtnInQRScanner.setText("Добавить товар");
        }


        mSellProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBoolean){
                    saveProduct();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.container, new CalculateProductFragment());
                    transaction.addToBackStack("QRScannner");
                    transaction.commit();
                }

            }
        });


        scannerView = view.findViewById(R.id.scannerView);



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

        if(list==null){
            list = new ArrayList<>();
            setInitialData();
        }
    }

    public void setInitialData(){
        list.add(new Products("Milk", "200","2", R.drawable.milk));
        list.add(new Products("Coca Cola", "300","2", R.drawable.cola));

    }


    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getActivity(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
    }
    @Override
    public void onResume() {
        super.onResume();

        if (checkPermission()) {
            if(scannerView == null) {
                scannerView = view.findViewById(R.id.scannerView);
            }
            scannerView.setResultHandler(this);
            scannerView.startCamera();
        } else {
            requestPermission();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getActivity(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }



    @Override
    public void handleResult(Result result) {
        Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
        if(mBoolean) {
            Toast.makeText(getActivity(), "Добавлено", Toast.LENGTH_SHORT).show();
            scannerView.resumeCameraPreview(QRScannerFragment.this);
            list.add(new Products(result.getText().toString(),"200","2", R.drawable.cola));
        }
        else
            {
            Bundle bundle = new Bundle();
            bundle.putString("QR_CODE_RESULT",result.getText().toString());
            scannerView.stopCamera();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            AddProductFragment addProductFragment = new AddProductFragment();
            addProductFragment.setArguments(bundle);
            transaction.replace(R.id.container, addProductFragment);
            transaction.addToBackStack("QRScanner");
            transaction.commit();
        }
    }
}
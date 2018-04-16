package com.example.askar.KanatTole.Fragments.QrCodeFragment;


import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.askar.KanatTole.Models.Products;
import com.example.askar.task3bottomnavigationview.R;
import com.example.askar.KanatTole.Utils.adapters.TotalProductAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class TotalProductFragment extends Fragment implements View.OnClickListener, FragmentManager.OnBackStackChangedListener {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    FragmentManager fragmentManager;

    private List<Products> list ;

    private Button cardPaymentBtn;
    private Button moneyPaymentBtn;
    private Button printBtn;
    private Button successConfirm;

    private Button successTotalDialogBtn;
    private Button canselTotalDialogBtn;

    private LinearLayout line2;
    private LinearLayout line3;
    private LinearLayout line1;

    private TextView addNewProduct;
    private TextView selectProduct;
    private TextView totalMoney;

    EditText inputCurrentMoney;

    SharedPreferences sPref;

    private TotalProductAdapter mAdapter;

    Dialog dialog;

    int count = 0;
    int totalNumber;
    int currentNumber=0;
    int price=0;
    int totalPrice=0;

    public TotalProductFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_total_product, container, false);
        mToolbar = view.findViewById(R.id.total_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        cardPaymentBtn=view.findViewById(R.id.totalProductCardPayment);
        moneyPaymentBtn=view.findViewById(R.id.totalProductMoneyPayment);
        printBtn=view.findViewById(R.id.totalProductPrintBtn);

        loadProduct();

        cardPaymentBtn.setOnClickListener(this);
        moneyPaymentBtn.setOnClickListener(this);
        printBtn.setOnClickListener(this);

        line2 = view.findViewById(R.id.totalLine2);
        line3 = view.findViewById(R.id.totalLine3);
        line3.setVisibility(View.GONE);

        addNewProduct = view.findViewById(R.id.addNewProductInTotalFragment);
        totalMoney = view.findViewById(R.id.totalNumber);
        addNewProduct.setOnClickListener(this);

        mRecyclerView = view.findViewById(R.id.totalProductRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new TotalProductAdapter(list,getActivity());
        mRecyclerView.setAdapter(mAdapter);

        totalNumber=getPrice();
        totalMoney.setText("Общая сумма: "+totalNumber);
        return view;
    }

    public int getPrice(){
        int price=0;
        for (int i=0;i<list.size();i++){
            Products products = list.get(i);
            price+=Integer.parseInt(products.getPrice())*Integer.parseInt(products.getCount());
        }

        return price;
    }

    @Override
    public void onClick(View v) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.totalProductMoneyPayment:
                showDialog();
                break;
            case R.id.totalProductCardPayment:
                cardWithPayDilaog();
                break;
            case R.id.totalProductPrintBtn:
                showDialog();
                break;

            case R.id.addNewProductInTotalFragment:
                transaction.replace(R.id.container,new CalculateProductFragment());
                transaction.commit();
                break;



        }
    }

    public void cardWithPayDilaog() {
        final Dialog dialogSilencer2 = new Dialog(getActivity());
        dialogSilencer2.setCancelable(false);
        dialogSilencer2.setContentView(R.layout.total_successing_dialog);
        Button totalSuccess = dialogSilencer2.findViewById(R.id.total_successing_new_add_product);
        TextView text = dialogSilencer2.findViewById(R.id.totalSuccessText);
        text.setText("Успешно снято!");
        totalSuccess.setText("Новая продажа");
        totalSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSilencer2.cancel();
                line3.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
                count=0;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container,new QRScannerFragment());
                transaction.commit();
            }
        });
        dialogSilencer2.show();
    }


    public void showDialog(){

        if(count==0){
            dialog = new Dialog(getActivity());
            dialog.setCancelable(true);

            dialog.setContentView(R.layout.total_calculating_dialog);

            canselTotalDialogBtn = dialog.findViewById(R.id.total_dialog_cansel);
            successTotalDialogBtn = dialog.findViewById(R.id.total_dialog_success);

            inputCurrentMoney = dialog.findViewById(R.id.totalPriceForPay);

            canselTotalDialogBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();

                }
            });

            successTotalDialogBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        currentNumber = Integer.parseInt(inputCurrentMoney.getText().toString());
                    }
                    catch (Exception e){
                        currentNumber=0;
                    }

                    if(currentNumber>=totalNumber){
                        dialog.cancel();
                        price =currentNumber-totalNumber;
                        totalPrice = totalNumber-currentNumber;
                        savePrice(totalPrice);
                        final Dialog dialogSuccess = new Dialog(getActivity());
                        dialogSuccess.setCancelable(false);
                        dialogSuccess.setContentView(R.layout.total_calculating_success_dialog);
                        TextView totalPriceSurrender = dialogSuccess.findViewById(R.id.totalPriceSurrender);
                        totalPriceSurrender.setText(""+price+" тг");

                        successConfirm = dialogSuccess.findViewById(R.id.total_dialog_surrender_success_btn);
                        successConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                line3.setVisibility(View.VISIBLE);
                                line2.setVisibility(View.GONE);
                                count=1;
                                dialogSuccess.cancel();
                            }
                        });
                        dialogSuccess.show();
                    }
                    else{
                        Toast.makeText(getActivity(), "У вас не хватает денег", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            dialog.show();

        }
        if(count==1){
            final Dialog dialogSilencer = new Dialog(getActivity());
            dialogSilencer.setCancelable(false);
            dialogSilencer.setContentView(R.layout.total_successing_dialog);
            Button totalSuccess = dialogSilencer.findViewById(R.id.total_successing_new_add_product);
            totalSuccess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogSilencer.cancel();
                    line3.setVisibility(View.GONE);
                    line2.setVisibility(View.VISIBLE);
                    count=0;
                    fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.container, new QRScannerFragment());
                    transaction.commit();
                }
            });
            dialogSilencer.show();
        }
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
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, new QRScannerFragment());
        transaction.commit();
    }

    public void savePrice(int savePrice){
        sPref = getActivity().getSharedPreferences("productsSharedRef",getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt("PRICE",savePrice);
        editor.apply();
    }
}

package com.example.askar.KanatTole.Fragments.MoreFragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.askar.KanatTole.API.Models.Items;
import com.example.askar.KanatTole.API.Services.API_client;
import com.example.askar.KanatTole.API.Services.API_routes;
import com.example.askar.KanatTole.LoginRegistration.AccountData;
import com.example.askar.task3bottomnavigationview.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class DetailProductFragment extends Fragment {
    private Button btn,btn_save;
    private TextView change_toolbar,back_product;
    private EditText name_product,amount,amountnote,buymuch,buyone;

    private Dialog dialog;
    static android.support.v7.app.AlertDialog.Builder mbuilder;

    private View view;

    public static List<Items> item_list=ProductListFragment.real_item;
    private ImageView imageView;
    public Intent intent;
    public int index;
    public Items items;

    SharedPreferences sharedPreferences;

    private Fragment me;
    ProductListFragment productListFragment;
    FragmentTransaction transaction;

    public DetailProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Bundle bundle = getArguments();
        if (bundle != null){
            items = (Items) bundle.get("Product");
        }

        view=inflater.inflate(R.layout.fragment_detail_product, container, false);
        me = this;
        name_product=(EditText) view.findViewById(R.id.det_name);
        name_product.setText(items.getItemTitle());


        change_toolbar=(TextView) view.findViewById(R.id.change_toolbar);

        buymuch=(EditText) view.findViewById(R.id.buymuch);
        buymuch.setText(items.getItemPurchaseCost());//////

        buyone=(EditText) view.findViewById(R.id.buyone);
        buyone.setText(items.getItemRetailCost());

        amount=(EditText) view.findViewById(R.id.amount);
        amount.setText(items.getItemAmount());

        amountnote=(EditText) view.findViewById(R.id.amountnote);

        Gson gson=new Gson();
        SharedPreferences prefs = getActivity().getSharedPreferences("detailshare",MODE_PRIVATE);
        String storedHashMapString = prefs.getString("productamount", "oopsDintWork");
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> testHashMap2 = gson.fromJson(storedHashMapString, type);

        amountnote.setText(testHashMap2.get(items.getItemId())+"");

        imageView=(ImageView) view.findViewById(R.id.imageView);

        btn=(Button) view.findViewById(R.id.button_main);
        btn_save=(Button) view.findViewById(R.id.button_save);


        back_product=(TextView) view.findViewById(R.id.back);
        back_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                ProductListFragment fragment = new ProductListFragment();
                FragmentTransaction transaction = ((AppCompatActivity)getActivity()).getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });

        for(int i=0;i<item_list.size();i++){
            if (item_list.get(i).getItemTitle().equals(name_product.getText().toString())){
                index=i;
            }
        }



        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(final View v) {
                mbuilder= new android.support.v7.app.AlertDialog.Builder(getActivity());
                View mView =getLayoutInflater().inflate(R.layout.custom_dialog,null);
                mbuilder.setView(mView);
                dialog=mbuilder.create();
                dialog.show();

                Rect displayRectangle = new Rect();
                Window window = getActivity().getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                dialog.getWindow().setLayout((int)(displayRectangle.width() *
                        0.95f), (int)(displayRectangle.height() * 0.36f));

                Button cancel =(Button) mView.findViewById(R.id.dialog_cancel);
                Button confirm = (Button)  mView.findViewById(R.id.dialog_confirm);

                TextView wanna =(TextView) mView.findViewById(R.id.wanna);
                wanna.setText("Вы хотите удалить этот товар и вернуться к списку товаров?");
                wanna.setTextSize(18);

                TextView product_name_dialog=(TextView) mView.findViewById(R.id.product_name_dialog);
                product_name_dialog.setText(name_product.getText().toString());
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        dialog.dismiss();
                        item_list.remove(index);


                        saveData();

                        API_routes api = API_client.getClient().create(API_routes.class);
                        Call<JsonObject> call = api.deleteItem("1",
                                items.getItemId());
                        if (isNetworkConnected()){
                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Log.d("myTag","Response :\n"+response.body());
                                    Snackbar.make(v, "Успешно удалено!",
                                            Snackbar.LENGTH_LONG).show();
                                }
                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Log.d("myTag", "Error : "+t.getMessage());
                                    Toast.makeText( getActivity(), "Ошибка(:", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(getActivity(), "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                        }

                        ProductListFragment fragment = new ProductListFragment();
                        FragmentTransaction transaction = ((AppCompatActivity)getActivity()).getSupportFragmentManager()
                                .beginTransaction();
                        transaction.replace(R.id.container, fragment);

                        transaction.commit();

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


        change_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back();
                        items=new Items(item_list.get(index).getItemId(),
                                name_product.getText().toString(),
                                item_list.get(index).getItemManufacturer(),
                                item_list.get(index).getItemBarCode(),
                                item_list.get(index).getItemRetailCost(),
                                buymuch.getText().toString(),
                                amount.getText().toString());
                        item_list.set(index,items);

                        saveData();


                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("title",name_product.getText().toString());
                        jsonObject.addProperty("manufacturer",items.getItemManufacturer().toString());
                        jsonObject.addProperty("barCode",items.getItemBarCode().toString());
                        jsonObject.addProperty("purchaseCost",buymuch.getText().toString());
                        jsonObject.addProperty("retailCost",buyone.getText().toString());
                        jsonObject.addProperty("amount",amount.getText().toString());
                        jsonObject.addProperty("id",items.getItemId().toString());
                        //getCurrentAccountData(getActivity()).getId().toString()
                        API_routes api = API_client.getClient().create(API_routes.class);
                        Call<JsonObject> call = api.editItem(jsonObject,"1",
                                item_list.get(index).getItemId());

                        if (isNetworkConnected()){
                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Log.d("myTag","Response :\n"+response.body());
                                    Snackbar.make(view, "Успешно изменено!",
                                            Snackbar.LENGTH_LONG).show();
                                }
                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Log.d("myTag", "Error : "+t.getMessage());
                                    Toast.makeText( getActivity(), "Ошибка(:", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(getActivity(), "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
                        }



                        Gson gson=new Gson();
                        SharedPreferences prefs = getActivity().getSharedPreferences("detailshare",MODE_PRIVATE);
                        String storedHashMapString = prefs.getString("productamount", "oopsDintWork");
                        Type type = new TypeToken<HashMap<String,String>>(){}.getType();
                        HashMap<String, String> testHashMap2 = gson.fromJson(storedHashMapString, type);
                        testHashMap2.put(items.getItemId(),amountnote.getText().toString());

                        String hashMapString = gson.toJson(testHashMap2);
                        prefs.edit().putString("productamount", hashMapString).apply();

                    }
                });
            }
        });
        return view;
    }
    private void saveData(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(item_list);
        editor.putString("task list",json);
        editor.apply();

    }
    private void saveamount(){
        HashMap<String, String> testHashMap = new HashMap<String, String>();
        for(int i=0;i<item_list.size();i++){
            testHashMap.put(item_list.get(i).getItemId()+"","100");
        }
        //convert to string using gson
        Gson gson = new Gson();
        String hashMapString = gson.toJson(testHashMap);

        //save in shared prefs
        SharedPreferences prefs = getActivity().getSharedPreferences("detailshare",MODE_PRIVATE);
        prefs.edit().putString("productamount", hashMapString).apply();
        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
    }
    private void loadData(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("task list",null);
        Type type=new TypeToken<ArrayList<Items>>(){}.getType();
        item_list=gson.fromJson(json,type);
    }
    public void change(){
        name_product.setClickable(true);
        name_product.setCursorVisible(true);
        name_product.setFocusableInTouchMode(true);
        name_product.setFocusable(true);

        buymuch.setClickable(true);
        buymuch.setCursorVisible(true);
        buymuch.setFocusableInTouchMode(true);
        buymuch.setFocusable(true);

        buyone.setClickable(true);
        buyone.setCursorVisible(true);
        buyone.setFocusableInTouchMode(true);
        buyone.setFocusable(true);

        amountnote.setClickable(true);
        amountnote.setCursorVisible(true);
        amountnote.setFocusableInTouchMode(true);
        amountnote.setFocusable(true);

        amount.setClickable(true);
        amount.setCursorVisible(true);
        amount.setFocusableInTouchMode(true);
        amount.setFocusable(true);

        name_product.setTextColor(Color.parseColor("#EA4F4F"));
        buymuch.setTextColor(Color.parseColor("#EA4F4F"));
        buyone.setTextColor(Color.parseColor("#EA4F4F"));
        amount.setTextColor(Color.parseColor("#EA4F4F"));
        amountnote.setTextColor(Color.parseColor("#EA4F4F"));

        change_toolbar.setTextColor(Color.parseColor("#4EBAC7"));

        btn.setVisibility(View.INVISIBLE);
        btn.setEnabled(false);

        btn_save.setVisibility(View.VISIBLE);
        btn_save.setEnabled(true);

    }
    public void back(){
        name_product.setClickable(false);
        name_product.setCursorVisible(false);
        name_product.setFocusableInTouchMode(false);
        name_product.setFocusable(false);
        name_product.setTextColor(Color.parseColor("#4A4A4A"));

        amount.setClickable(false);
        amount.setCursorVisible(false);
        amount.setFocusableInTouchMode(false);
        amount.setFocusable(false);
        amount.setTextColor(Color.parseColor("#4A4A4A"));

        amountnote.setClickable(false);
        amountnote.setCursorVisible(false);
        amountnote.setFocusableInTouchMode(false);
        amountnote.setFocusable(false);
        amountnote.setTextColor(Color.parseColor("#4A4A4A"));

        buyone.setClickable(false);
        buyone.setCursorVisible(false);
        buyone.setFocusableInTouchMode(false);
        buyone.setFocusable(false);
        buyone.setTextColor(Color.parseColor("#4A4A4A"));

        buymuch.setClickable(false);
        buymuch.setCursorVisible(false);
        buymuch.setFocusableInTouchMode(false);
        buymuch.setFocusable(false);
        buymuch.setTextColor(Color.parseColor("#4A4A4A"));

        btn_save.setVisibility(View.INVISIBLE);
        btn_save.setEnabled(false);

        btn.setVisibility(View.VISIBLE);
        btn.setEnabled(true);

        change_toolbar.setTextColor(Color.parseColor("#9B9B9B"));

    }
    public static AccountData getCurrentAccountData(Context context){
        SharedPreferences  sharedPreferences=context.getSharedPreferences("CurrentAccountData",context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("","");
        if(json.equalsIgnoreCase("")){
            return null;
        }
        AccountData accountData=gson.fromJson(json,AccountData.class);
        return accountData;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }



}

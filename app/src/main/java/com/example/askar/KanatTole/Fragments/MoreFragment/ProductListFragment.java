package com.example.askar.KanatTole.Fragments.MoreFragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.askar.KanatTole.API.Models.Items;
import com.example.askar.KanatTole.API.Services.API_client;
import com.example.askar.KanatTole.API.Services.API_routes;
import com.example.askar.KanatTole.LoginRegistration.AccountData;
import com.example.askar.KanatTole.Utils.adapters.adapters.RecyclerAdapterCheckbox;
import com.example.askar.KanatTole.Utils.adapters.adapters.RecyclerAdapterNew;
import com.example.askar.task3bottomnavigationview.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {
    private static final String TAG = "ProductListFragment";
    private TextView main_select,main_back;
    private EditText search;

    public RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    RecyclerView.Adapter adapter;
    static AlertDialog.Builder mbuilder,success;
    Dialog dialog,dialogS;

    ProgressDialog progressDialog;

    StringBuffer stringBuffer=null;
    StringBuffer sb=null;

    private View view;


    public static List<Items> real_item;
    public static List<Items> share_real_item;

    public static List<Items> clicked_item_list=new ArrayList<>();
    DetailProductFragment detailProductFragment;

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_product_list, container, false);
        main_back=(TextView) view.findViewById(R.id.main_back);
        main_select=(TextView) view.findViewById(R.id.main_select);
        search=(EditText) view.findViewById(R.id.search_product);

        real_item=new ArrayList<>();

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Загрузка");
        progressDialog.setMessage("Загружаем товары,пожалуйста подождите!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        //Toast.makeText(getActivity(), getCurrentAccountData(getActivity()).getId(), Toast.LENGTH_SHORT).show();


        API_routes api = API_client.getClient().create(API_routes.class);
        Call<JsonArray> call = api.getItemFromCompany("1");
        if (isNetworkConnected()){
            call.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    SharedPreferences preferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
                    preferences.edit().remove("task list").commit();
                    Log.d("myTag", "Response : "+response.body());
                    progressDialog.dismiss();
                    JsonArray json=response.body();
                    Gson gson=new Gson();
                    Type type=new TypeToken<ArrayList<Items>>(){}.getType();
                    real_item=gson.fromJson(json,type);

                    //Save main
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("shared preferences",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    Gson savegson=new Gson();
                    String savejson=savegson.toJson(real_item);
                    editor.putString("task list",savejson);
                    editor.apply();

                    //Toast.makeText(getActivity(), "Successfully saved to shared preference", Toast.LENGTH_SHORT).show();
                    recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    adapter=new RecyclerAdapterNew(real_item,getActivity());
                    recyclerView.setAdapter(adapter);

                    saveamount();

//                    if(testHashMap2.size()!=Integer.parseInt(jsonSizeRead)){
//                        saveamount();
//                        editorSize.putString("task size",saveamount()+"");
//                        editorSize.apply();
//                        Toast.makeText(getActivity(), "if", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(getActivity(), "else", Toast.LENGTH_SHORT).show();
//                    }

                    //read from amount share
                    Gson gsonmap=new Gson();
                    SharedPreferences prefs = getActivity().getSharedPreferences("detailshare",MODE_PRIVATE);
                    String storedHashMapString = prefs.getString("productamount", "oopsDintWork");
                    Type typeS = new TypeToken<HashMap<String, String>>(){}.getType();
                    HashMap<String, String> testHashMap2 = gsonmap.fromJson(storedHashMapString, typeS);
                    Toast.makeText(getActivity(), testHashMap2.size()+
                            "", Toast.LENGTH_SHORT).show();

                    //save size
                    SharedPreferences sharedPreferencesSize=getActivity().getSharedPreferences("shared size",MODE_PRIVATE);
                    SharedPreferences.Editor editorSize=sharedPreferencesSize.edit();
                    editorSize.putString("task size",saveamount()+"");
                    editorSize.apply();
                    String jsonSizeRead=sharedPreferencesSize.getString("task size",null);



                }
                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    Log.d("myTag", "Error : "+t.getMessage());
                    progressDialog.dismiss();
                    Toast.makeText( getActivity(), "Ошибка(:", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Нет подключения к интернету", Toast.LENGTH_SHORT).show();

            Gson gson=new Gson();
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("shared preferences",MODE_PRIVATE);
            String json=sharedPreferences.getString("task list",null);
            Type type=new TypeToken<ArrayList<Items>>(){}.getType();
            real_item=gson.fromJson(json,type);

            if(getActivity().getSharedPreferences("detailshare",MODE_PRIVATE).getAll().size()<=0){
                saveamount();
                Toast.makeText(getActivity(), "if", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "else", Toast.LENGTH_SHORT).show();
            }

            recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter=new RecyclerAdapterNew(real_item,getActivity());
            recyclerView.setAdapter(adapter);

            Log.d("myTag", "No internet connection");
        }
        search();


        main_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicked_item_list.size()!=0){
                    mbuilder= new AlertDialog.Builder(getActivity());
                    View mView =getLayoutInflater().inflate(R.layout.custom_dialog,null);
                    mbuilder.setView(mView);
                    dialog=mbuilder.create();
                    dialog.show();

                    Rect displayRectangle = new Rect();
                    Window window = getActivity().getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);


                    dialog.getWindow().setLayout((int)(displayRectangle.width() *
                            0.95f), (int)(displayRectangle.height() * 0.38f));

                    stringBuffer=new StringBuffer();
                    StringBuffer sb=new StringBuffer();
                    for(Items product: clicked_item_list){
                        stringBuffer.append(product.getItemTitle());
                        stringBuffer.append( ",");

                    }
                    StringBuffer news=new StringBuffer();
                    news.append(stringBuffer.substring(0,stringBuffer.length()-1));


                    TextView product_name = (TextView) mView.findViewById(R.id.product_name_dialog);
                    if(stringBuffer.length()>30){
                        sb=new StringBuffer();
                        sb.append(stringBuffer.substring(0,30)+"...");
                        product_name.setText(sb.toString());
                        sb.setLength(0);
                    }
                    else{
                        product_name.setText(news.toString());

                    }
                    Button cancel =(Button) mView.findViewById(R.id.dialog_cancel);
                    Button confirm = (Button)  mView.findViewById(R.id.dialog_confirm);

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            if(clicked_item_list.size()>0){
                                for(int i=0;i<clicked_item_list.size();i++){
                                    API_routes api = API_client.getClient().create(API_routes.class);
                                    Call<JsonObject> call = api.deleteItem("1",
                                            clicked_item_list.get(i).getItemId());
                                    final int currentProduct=i;
                                    call.enqueue(new Callback<JsonObject>() {
                                        @Override
                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                            Log.d("myTag",response.body()+"");
                                            clicked_item_list.remove(currentProduct);
                                        }

                                        @Override
                                        public void onFailure(Call<JsonObject> call, Throwable t) {

                                        }
                                    });

                                }
                            }
                            success= new AlertDialog.Builder(getActivity());
                            View mView =getLayoutInflater().inflate(R.layout.success_delete,null);
                            success.setView(mView);
                            dialogS=success.create();

                            Rect displayRectangle = new Rect();
                            Window window = getActivity().getWindow();
                            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

                            dialogS.getWindow().setLayout((int)(displayRectangle.width() *
                                    0.95f), (int)(displayRectangle.height() * 0.36f));

                            Button ok =(Button) mView.findViewById(R.id.dialog_success);

                            if(clicked_item_list.size()==0){
                                dialogS.show();

                            }
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogS.dismiss();
                                    main_back.setEnabled(false);
                                    main_back.setVisibility(View.INVISIBLE);
                                    main_select.setTextColor(Color.parseColor("#9B9B9B"));

                                    main_select.setEnabled(true);
                                    main_select.setVisibility(View.VISIBLE);
                                    main_select.setText("Выбрать");

                                    recyclerView.setHasFixedSize(true);
                                    layoutManager = new LinearLayoutManager(getActivity());
                                    recyclerView.setLayoutManager(layoutManager);
                                    adapter = new RecyclerAdapterNew(real_item,getActivity());
                                    recyclerView.setAdapter(adapter);
                                    saveData();
                                    Toast.makeText(getActivity(), real_item.size()+"", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            main_select.setEnabled(true);
                            main_select.setVisibility(View.VISIBLE);

                            dialog.dismiss();
                        }
                    });
                }
                else{
                    Snackbar.make(v, "Выберите товары которые хотите удалить!",
                            Snackbar.LENGTH_LONG).show();

                }

            }
        });

        main_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(main_select.getCurrentTextColor()==Color.parseColor("#9B9B9B")){
                    main_select.setText("Назад");
                    recyclerView.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new RecyclerAdapterCheckbox(clicked_item_list,real_item,getActivity());
                    recyclerView.setAdapter(adapter);

                    search.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence query, int start, int before, int count) {
                            query=query.toString().toLowerCase();
                            final List<Items> product_list=new ArrayList<Items>();
                            for(int i=0;i<real_item.size();i++){
                                final String textName=real_item.get(i).getItemTitle().toLowerCase();
                                if(textName.contains(query)){
                                    product_list.add(new Items(real_item.get(i).getItemId(),
                                            real_item.get(i).getItemTitle(),
                                            real_item.get(i).getItemManufacturer(),
                                            real_item.get(i).getItemBarCode(),
                                            real_item.get(i).getItemRetailCost(),
                                            real_item.get(i).getItemPurchaseCost(),
                                            real_item.get(i).getItemAmount()));
                                }
                                Collections.sort(product_list, new Comparator<Items>() {
                                    @Override
                                    public int compare(Items o1, Items o2) {
                                        return o1.getItemTitle().compareTo(o2.getItemTitle());
                                    }
                                });
                                recyclerView.setHasFixedSize(true);
                                layoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(layoutManager);
                                adapter = new RecyclerAdapterCheckbox(product_list,product_list,getActivity());
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {


                        }
                    });

                    main_select.setTextColor(Color.parseColor("#4EBAC8"));


                    main_back.setVisibility(View.VISIBLE);
                    main_back.setEnabled(true);




                }
                else if(main_select.getCurrentTextColor()==Color.parseColor("#4EBAC8")){
                    main_back.setEnabled(false);
                    main_back.setVisibility(View.INVISIBLE);
                    clicked_item_list.clear();

                    recyclerView.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new RecyclerAdapterNew(real_item,getActivity());
                    recyclerView.setAdapter(adapter);
                    main_select.setText("Выбрать");
                    main_select.setTextColor(Color.parseColor("#9B9B9B"));
                    search();

                }
            }
        });

        return view;
    }
    private void saveData(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(real_item);
        editor.putString("task list",json);
        editor.apply();

    }
    private void loadData(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("task list",null);
        Type type=new TypeToken<ArrayList<Items>>(){}.getType();
        real_item=gson.fromJson(json,type);
     }
    private int saveamount(){
        HashMap<String, String> testHashMap = new HashMap<String, String>();
        for(int i=0;i<real_item.size();i++){
            testHashMap.put(real_item.get(i).getItemId()+"","100");
        }
        //convert to string using gson
        Gson gson = new Gson();
        String hashMapString = gson.toJson(testHashMap);

        //save in shared prefs
        SharedPreferences prefs = getActivity().getSharedPreferences("detailshare",MODE_PRIVATE);
        prefs.edit().putString("productamount", hashMapString).apply();
        return testHashMap.size();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void search(){
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence query, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query=query.toString().toLowerCase();
                final List<Items> product_list=new ArrayList<>();
                for(int i=0;i<real_item.size();i++){
                    final String textName=real_item.get(i).getItemTitle().toLowerCase();
                    if(textName.contains(query)){
                        product_list.add(new Items(real_item.get(i).getItemId(),
                                real_item.get(i).getItemTitle(),
                                real_item.get(i).getItemManufacturer(),
                                real_item.get(i).getItemBarCode(),
                                real_item.get(i).getItemRetailCost(),
                                real_item.get(i).getItemPurchaseCost(),
                                real_item.get(i).getItemAmount()));
                    }
                    Collections.sort(product_list, new Comparator<Items>() {
                        @Override
                        public int compare(Items o1, Items o2) {
                            return o1.getItemTitle().compareTo(o2.getItemTitle());
                        }
                    });
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    adapter = new RecyclerAdapterNew(product_list,getActivity());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
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

}

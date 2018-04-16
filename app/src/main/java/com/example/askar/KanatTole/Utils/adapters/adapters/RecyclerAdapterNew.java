package com.example.askar.KanatTole.Utils.adapters.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.askar.KanatTole.API.Models.Items;
import com.example.askar.KanatTole.Fragments.MoreFragment.DetailProductFragment;
import com.example.askar.task3bottomnavigationview.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class RecyclerAdapterNew extends RecyclerView.Adapter<RecyclerAdapterNew.ViewHolder> {

    public List<Items> real_item;
    public Context context;

    public RecyclerAdapterNew(List<Items> real_item, Context context) {
        this.real_item = real_item;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;
        public TextView amount;
        public ImageView image;



        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.product_name);
            price = (TextView) itemView.findViewById(R.id.product_price);
            amount = (TextView) itemView.findViewById(R.id.product_amount);
            image = (ImageView) itemView.findViewById(R.id.product_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailProductFragment fragment = new DetailProductFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Product", real_item.get(getAdapterPosition()));
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left_exit);
                    transaction.replace(R.id.container, fragment, context.getString(R.string.detail_product_fragment));
                    transaction.addToBackStack("back");
                    transaction.commit();

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.amount.setText(real_item.get(i).getItemAmount()+"шт");
        viewHolder.price.setText(real_item.get(i).getItemPurchaseCost()+"тг");

        String s = real_item.get(i).getItemTitle().toString();
        int k = real_item.get(i).getItemTitle().toString().indexOf(" ", s.indexOf(" ") + 1);
        int count=0;
        for(int j=0;j<real_item.get(i).getItemTitle().toString().length();j++){
            if(real_item.get(i).getItemTitle().toString().charAt(j)==32){
                count++;
            }
        }
        if(count>1){
            viewHolder.name.setText(real_item.get(i).getItemTitle().toString().substring(0,k));
        }
        else{
            viewHolder.name.setText(real_item.get(i).getItemTitle().toString());
        }

        Gson gsonmap=new Gson();
        SharedPreferences prefs = context.getSharedPreferences("detailshare",MODE_PRIVATE);
        String storedHashMapString = prefs.getString("productamount", "oopsDintWork");
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> testHashMap2 = gsonmap.fromJson(storedHashMapString, type);


        String a=testHashMap2.get(real_item.get(i).getItemId());
        int a1=Integer.parseInt(a);
        int b=Integer.parseInt(real_item.get(i).getItemAmount());
        if(b>=a1){
            viewHolder.amount.setTextColor(Color.parseColor("#4EBAC7"));
        }
        if(b<a1){
            viewHolder.amount.setTextColor(Color.parseColor("#EA4F4F"));
        }
        if(a1 > b*0.8 && a1 < b){
            viewHolder.amount.setTextColor(Color.parseColor("#f2cf0c"));
        }


    }
    public void removeFragment(Fragment fragment){
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
    private void hashmaptest()
    {
        //create test hashmap
        HashMap<Integer, Integer> testHashMap = new HashMap<Integer, Integer>();
        testHashMap.put(7,1000);
        testHashMap.put(8, 25);
        testHashMap.put(2, 35);
        testHashMap.put(3, 530);
        testHashMap.put(6, 25);

        //convert to string using gson
        Gson gson = new Gson();
        String hashMapString = gson.toJson(testHashMap);

        //save in shared prefs
        SharedPreferences prefs = context.getSharedPreferences("detailshare",MODE_PRIVATE);
        prefs.edit().putString("productamount", hashMapString).apply();

        //get from shared prefs
        String storedHashMapString = prefs.getString("productamount", "oopsDintWork");
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> testHashMap2 = gson.fromJson(storedHashMapString, type);

        //use values
        String toastString = testHashMap2.get("key1") + " | " + testHashMap2.get("key2");
        Toast.makeText(context, toastString, Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return real_item.size();
    }


}

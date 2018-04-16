package com.example.askar.KanatTole.Utils.adapters.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.askar.KanatTole.API.Models.Items;
import com.example.askar.task3bottomnavigationview.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class RecyclerAdapterCheckbox extends RecyclerView.Adapter<RecyclerAdapterCheckbox.ViewHolder> {

    public List<Items> real_item;
    public List<Items> clicked_real_item;
    public Context context;
    public Boolean a=false;



    public RecyclerAdapterCheckbox(List<Items> clicked_real_item, List<Items> real_item, Context context) {
        this.real_item = real_item;
        this.clicked_real_item= clicked_real_item;
        this.context = context;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView price;
        public TextView amount;
        public ImageView image;
        public CheckBox checkBox;
        ItemClickListener itemClickListener;



        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.product_name);
            price = (TextView) itemView.findViewById(R.id.product_price);
            amount = (TextView) itemView.findViewById(R.id.product_amount);
            image = (ImageView) itemView.findViewById(R.id.product_image);
            checkBox = (CheckBox) itemView.findViewById(R.id.product_checkbox);

            checkBox.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        clicked_real_item.remove(real_item.get(getAdapterPosition()));
                        checkBox.setChecked(false);
                    }
                    else{
                        clicked_real_item.add(real_item.get(getAdapterPosition()));
                        checkBox.setChecked(true);
                    }
                    }
            });
        }
        public void setItemCLickListener(ItemClickListener ic){
            this.itemClickListener=ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_layout_with_checkbox, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
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


        Gson gson=new Gson();
        SharedPreferences prefs = context.getSharedPreferences("detailshare",MODE_PRIVATE);
        String storedHashMapString = prefs.getString("productamount", "oopsDintWork");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> testHashMap2 = gson.fromJson(storedHashMapString, type);

        viewHolder.amount.setText(real_item.get(i).getItemAmount()+"шт");
        viewHolder.price.setText(real_item.get(i).getItemPurchaseCost()+"тг");
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

        viewHolder.setItemCLickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox chk=(CheckBox) v;
                if(chk.isChecked()){
                    clicked_real_item.add(real_item.get(i));
                }
                else{
                    clicked_real_item.remove(real_item.get(i));
                }
            }
        });

        }

    @Override
    public int getItemCount() {
        return real_item.size();
    }


}

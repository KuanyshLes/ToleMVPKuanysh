package com.example.askar.KanatTole.Utils.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.askar.KanatTole.Models.Products;
import com.example.askar.task3bottomnavigationview.R;

import java.util.List;


public class TotalProductAdapter extends RecyclerView.Adapter<TotalProductAdapter.TotalViewHolder>{
    List<Products> lists;
    Context context;
    public int price;


    public TotalProductAdapter(List<Products> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public TotalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_total_product,parent,false);

        return new TotalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TotalViewHolder holder, int position) {
        Products products = lists.get(position);
        holder.title.setText(products.getTitle());
        holder.price.setText(""+(Integer.parseInt(products.getPrice())*Integer.parseInt(products.getCount())));
        holder.count.setText(products.getCount()+"x"+products.getPrice());


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public int getPrice(){
        return price;
    }


    public class TotalViewHolder extends RecyclerView.ViewHolder{

        TextView  title,price,count;

        public TotalViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.totalProductTitle);
            price = itemView.findViewById(R.id.totalProductPrice);
            count = itemView.findViewById(R.id.totalProductCount);


        }
    }
}

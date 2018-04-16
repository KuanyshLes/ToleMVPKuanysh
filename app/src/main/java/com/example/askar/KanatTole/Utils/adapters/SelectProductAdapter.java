package com.example.askar.KanatTole.Utils.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.askar.KanatTole.Models.Products;
import com.example.askar.task3bottomnavigationview.R;

import java.util.List;


public class SelectProductAdapter extends RecyclerView.Adapter<SelectProductAdapter.SelectViewHolder>{
    public static List<Products> lists;
    Context context;

    public SelectProductAdapter(List<Products> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_product,parent,false);
        return new SelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectViewHolder holder, final int position) {
        Products products = lists.get(position);
        holder.title.setText(products.getTitle());
        holder.price.setText(products.getPrice());
        holder.count.setText(products.getCount());

        holder.imageView.setImageResource(products.getImage());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lists.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,lists.size());
                notifyDataSetChanged();
            }
        });


    }
    @Override
    public int getItemCount() {
        return lists.size();
    }
    public class SelectViewHolder extends RecyclerView.ViewHolder{
        TextView  title,price,count;
        ImageView imageView;
        ImageButton imageButton;
        public SelectViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.selectProductTitle);
            price = itemView.findViewById(R.id.selectProductPrice);
            count = itemView.findViewById(R.id.selectProductCount);
            imageView = itemView.findViewById(R.id.selectImage);
            imageButton = itemView.findViewById(R.id.delete_image_btn);
        }
    }
}

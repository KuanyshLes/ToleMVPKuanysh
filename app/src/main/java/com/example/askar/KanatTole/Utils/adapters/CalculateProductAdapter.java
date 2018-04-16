package com.example.askar.KanatTole.Utils.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.askar.KanatTole.Models.Products;
import com.example.askar.task3bottomnavigationview.R;

import java.util.List;


public class CalculateProductAdapter extends RecyclerView.Adapter<CalculateProductAdapter.CalculateProductHolder> {
    Context context;
    Products products;
    public List<Products> list;
    public CalculateProductAdapter(Context context, List<Products> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CalculateProductHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_calculate_product,parent,false);
        return new CalculateProductHolder(view);
    }

    @Override
    public void onBindViewHolder(final CalculateProductAdapter.CalculateProductHolder holder, final int position) {
        products = list.get(position);
        holder.title.setText(products.getTitle());
        if(Integer.parseInt(products.getCount())==0){
            holder.count.setText("");
        }
        else {
            holder.count.setText("x"+products.getCount());
        }
        holder.price.setText(products.getPrice());

        holder.imageView.setImageResource(products.getImage());
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int m = Integer.parseInt(holder.count.getText().toString().replace("x", "").trim());
                m++;
                list.get(position).setCount("" + m);
                holder.count.setText("x"+m);



            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int m = Integer.parseInt(holder.count.getText().toString().replace("x", "").trim());
                if (m > 1) {
                    m--;
                    holder.count.setText("x" + m);
                    list.get(position).setCount("" + m);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CalculateProductHolder extends RecyclerView.ViewHolder{

        ImageView plus,minus;
        TextView price,count,title;
        ImageView imageView;

        public CalculateProductHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.calculateProductTitle);
            price = itemView.findViewById(R.id.calculateProductPrice);
            count = itemView.findViewById(R.id.calculateProductCount);

            imageView = itemView.findViewById(R.id.calculateImage);

            plus = itemView.findViewById(R.id.calcultePlusBtn);
            minus = itemView.findViewById(R.id.calculateMinusBtn);
        }
    }
}

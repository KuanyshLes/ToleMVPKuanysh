package com.example.askar.KanatTole.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.askar.KanatTole.Models.Item;
import com.example.askar.task3bottomnavigationview.R;

import java.util.ArrayList;

/**
 * Created by askar on 28.03.2018.
 */

public class ItemsRecyclerViewAdapter extends RecyclerView.Adapter<ItemsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "PhotoRecyclerViewAdapte";

    private ArrayList<Item> photos;
    private Context context;

    public ItemsRecyclerViewAdapter(ArrayList<Item> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        //private
        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.image.setImageResource(photos.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }



}

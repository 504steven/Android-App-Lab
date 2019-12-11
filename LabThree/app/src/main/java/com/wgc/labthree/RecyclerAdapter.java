package com.wgc.labthree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CafeItemViewHolder> {
//    private Context context;
    private String[] cafes;

    public  RecyclerAdapter( String[] cafes) {
//        this.context = context;
        this.cafes = cafes;
        for(String s : cafes)
        System.out.println("XXXXXXXXXXXXXX new RecyclerAdapter  XXXXXXXXXXXX::" + s);
    }

    @NonNull
    @Override
    public CafeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cafe_item, parent, false);
        return new CafeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CafeItemViewHolder holder, int position) {
//        CafeItemViewHolder cafeItemViewHolder = (CafeItemViewHolder)holder;
//        cafeItemViewHolder.cafeImageView.setImageURI(null);
        System.out.println("XXXXXXXXXXXXXX positions  XXXXXXXXXXXX::" + position);
        holder.cafeTextView.setText(cafes[position]);
    }

    @Override
    public int getItemCount() {
        return cafes.length;
    }

    public static class CafeItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout parentLayout;
        TextView cafeTextView;

        public CafeItemViewHolder(@NonNull View itemView) {
            super(itemView);
//            cafeImageView = itemView.findViewById(R.id.cafe_image);
            cafeTextView = itemView.findViewById(R.id.cafe_text);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

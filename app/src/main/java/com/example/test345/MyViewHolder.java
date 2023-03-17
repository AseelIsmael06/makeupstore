package com.example.test345;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder
{
    ImageView imageView;
    TextView name,price;

    public MyViewHolder(@NonNull View itemView)
    {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageView);
        name=itemView.findViewById(R.id.tvNameProductRow);
        price=itemView.findViewById(R.id.tvPriceProductRow);

    }
}

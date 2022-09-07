package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.SelectCarModel;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoAllCarBrands;
import com.bumptech.glide.Glide;


import java.util.ArrayList;

public class AdapterCarBrandList extends  RecyclerView.Adapter<AdapterCarBrandList.RecyclerViewHolder>{

    ArrayList<PojoAllCarBrands> allCarBrands;
    Context context;

    public AdapterCarBrandList(ArrayList<PojoAllCarBrands> allCarBrands, Context context) {
        this.allCarBrands = allCarBrands;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_car_makelist, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerViewHolder holder, int position)
    {
        PojoAllCarBrands recycler=allCarBrands.get(position);
        if (recycler.getBrand_icon() != null && !recycler.getBrand_icon().isEmpty() && !recycler.getBrand_icon().equals("null")) {
            Glide.with(context).load(recycler.getBrand_icon()).placeholder(R.drawable.icon_noimage).into(holder.brand_logo);
        }
        holder.tv_brandname.setText(recycler.getCar_brand());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.carbrandid=recycler.getId();
                SPHelper.brandlogo=recycler.getBrand_icon();
                Intent intent=new Intent(context.getApplicationContext(), SelectCarModel.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allCarBrands.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView brand_logo;
        TextView tv_brandname;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_brandname=itemView.findViewById(R.id.tv_brandname);
            brand_logo=itemView.findViewById(R.id.brand_logo);
        }
    }
}

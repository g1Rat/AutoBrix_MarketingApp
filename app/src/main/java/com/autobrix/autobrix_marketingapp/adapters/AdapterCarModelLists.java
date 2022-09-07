package com.autobrix.autobrix_marketingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.SelectCarModel;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoAllCarModels;


import java.util.ArrayList;

public class AdapterCarModelLists extends  RecyclerView.Adapter<AdapterCarModelLists.RecyclerViewHolder>{
    ArrayList<PojoAllCarModels> carModels;
    Context context;

    public AdapterCarModelLists(ArrayList<PojoAllCarModels> carModels, Context context) {
        this.carModels = carModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_carmodellists, parent, false);
        return new RecyclerViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull  RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PojoAllCarModels recyclerdata=carModels.get(position);
        holder.tv_modelname.setText(recyclerdata.getCar_model());

        if(carModels.get(position).getIsSelected().equals("y")){

            holder. rl_carmodel.setBackground(context.getDrawable(R.drawable.cardview_dealership));
            holder.tv_modelname.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.rl_carmodel.setBackground(context.getDrawable(R.drawable.cardview_lightgrey_margined));
             holder.tv_modelname.setTextColor(Color.parseColor("#FF000000"));
        }

        holder.rl_carmodel.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view)
            {
                SPHelper.carmodelid=recyclerdata.getModel_id();
                SPHelper.model_name=recyclerdata.getCar_model();

                for (int i=0;i<carModels.size();i++)
                {
                    if (i == position)
                    {
                        carModels.get(i).setIsSelected("y");

                    } else {
                        carModels.get(i).setIsSelected("n");
                    }
                }
               notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return carModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tv_modelname;
        RelativeLayout rl_carmodel;

        public RecyclerViewHolder(@NonNull  View itemView) {
            super(itemView);
            tv_modelname=itemView.findViewById(R.id.tv_modelname);
            rl_carmodel=itemView.findViewById(R.id.rl_carmodel);
        }
    }
}

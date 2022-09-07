package com.autobrix.autobrix_marketingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.AddDealerRequest;
import com.autobrix.autobrix_marketingapp.AddDailyUpdate;
import com.autobrix.autobrix_marketingapp.AddMarketMtrl;
import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.SearchDealer;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoDealers;

import java.util.ArrayList;

public  class AdapterSeachDealerList extends RecyclerView.Adapter<AdapterSeachDealerList.RecyclerViewHolder>
{
    ArrayList<PojoDealers> dealerlist;
    Context context;

    public AdapterSeachDealerList(ArrayList<PojoDealers> dealerlist, Context context) {
        this.dealerlist = dealerlist;
        this.context = context;

    }

    @NonNull
    @Override
    public AdapterSeachDealerList.RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_dealer_lists,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterSeachDealerList.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        PojoDealers recyclerData = dealerlist.get(position);
        holder.d_name.setText(recyclerData.getDealer_name());
        holder.d_phone.setText(recyclerData.getPhone_no());
        holder.d_location.setText(recyclerData.getFull_address());
//        holder.createdby.setVisibility(View.VISIBLE);
//        holder.createdno.setVisibility(View.VISIBLE);
//        holder.label_created.setVisibility(View.VISIBLE);
//        holder.createdby.setText(recyclerData.getEmployee_name());
//        holder.createdno.setText(recyclerData.getEmployee_phone_no());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SPHelper.dealername=recyclerData.getDealer_name();
                SPHelper.dealer_id=recyclerData.getDealer_id();
                if(SPHelper.camefrom.equals("insp")){
                    Intent intent=new Intent(context, AddDealerRequest.class);
                    context.startActivity(intent);
                    ((SearchDealer)context).finish();
                }
                else if(SPHelper.camefrom.equals("add")){
                    Intent intent=new Intent(context, AddMarketMtrl.class);
                    context.startActivity(intent);
                    ((SearchDealer)context).finish();
                }
                else{
                    Intent intent=new Intent(context, AddDailyUpdate.class);
                    context.startActivity(intent);
                    ((SearchDealer)context).finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dealerlist.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView d_logo,checkbox_empty,checkbox_checked;
        TextView d_name,label_created,d_phone,d_location,dealer_type,createdby,createdno;
        RelativeLayout rl1;
        public RecyclerViewHolder(@NonNull  View itemView)
        {
            super(itemView);
            d_logo=itemView.findViewById(R.id.d_logo);
            d_name=itemView.findViewById(R.id.d_name);
            d_phone=itemView.findViewById(R.id.d_phone);
            d_location=itemView.findViewById(R.id.d_location);
            dealer_type=itemView.findViewById(R.id.dealer_type);
            rl1=itemView.findViewById(R.id.rl1);
            createdby=itemView.findViewById(R.id.createdby);
            createdno=itemView.findViewById(R.id.createdno);
            label_created=itemView.findViewById(R.id.label_created);
        }
    }
}

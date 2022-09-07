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

import com.autobrix.autobrix_marketingapp.AddMarketMtrl;
import com.autobrix.autobrix_marketingapp.DealerListPage;
import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoDealers;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterDealerList extends RecyclerView.Adapter<AdapterDealerList.RecyclerViewHolder> {

    public  ArrayList<PojoDealers> dealerlist;
    Context context;
    public  String dealerid,confirm,dealername;
    public  Intent intent,intent4;

    public AdapterDealerList(ArrayList<PojoDealers> dealerlist, Context context) {
        this.dealerlist = dealerlist;
        this.context = context;

    }

    @NonNull
    @Override
    public AdapterDealerList.RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_dealer_lists,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterDealerList.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        DealerListPage obj=(DealerListPage) context;
        PojoDealers recyclerData = dealerlist.get(position);
        holder.d_name.setText(recyclerData.getDealer_name());
        holder.d_phone.setText(recyclerData.getPhone_no());
        holder.d_location.setText(recyclerData.getFull_address());
        if(obj.d_id.equals("")){
            holder.createdby.setVisibility(View.VISIBLE);
            holder.createdno.setVisibility(View.VISIBLE);
            holder.label_created.setVisibility(View.VISIBLE);
            holder.label_created_no.setVisibility(View.VISIBLE);
            holder.createdby.setText(recyclerData.getEmployee_name());
            holder.createdno.setText(recyclerData.getEmployee_phone_no());
        }else{
            holder.createdby.setVisibility(View.GONE);
            holder.createdno.setVisibility(View.GONE);
            holder.label_created.setVisibility(View.GONE);
            holder.label_created_no.setVisibility(View.GONE);
        }


        if (recyclerData.getDealer_logo() != null && !recyclerData.getDealer_logo().isEmpty() && !recyclerData.getDealer_logo().equals("null"))
        {
            Glide.with(context).load(recyclerData.getDealer_logo()).placeholder(R.drawable.icon_noimage).into(holder.d_logo);
        }
        //on item select
//        if(SPHelper.camefrom.equals("add"))
//        {
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view)
//                {
//                    SPHelper.dealername=recyclerData.getDealer_name();
//                    SPHelper.dealer_id=recyclerData.getDealer_id();
//                   Intent  intent=new Intent(context, AddMarketMtrl.class);
//                   context.startActivity(intent);
//                }
//            });
//        }else{
//        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView d_logo,checkbox_empty,checkbox_checked;
        TextView d_name,d_phone,d_location,createdby,createdno,label_created,label_created_no;
        RelativeLayout rl1;
        public RecyclerViewHolder(@NonNull  View itemView)
        {
            super(itemView);
            d_logo=itemView.findViewById(R.id.d_logo);
            d_name=itemView.findViewById(R.id.d_name);
            d_phone=itemView.findViewById(R.id.d_phone);
            d_location=itemView.findViewById(R.id.d_location);
            checkbox_empty=itemView.findViewById(R.id.checkbox_empty);
            checkbox_checked=itemView.findViewById(R.id.checkbox_checked);
            createdby=itemView.findViewById(R.id.createdby);
            createdno=itemView.findViewById(R.id.createdno);
            label_created=itemView.findViewById(R.id.label_created);
            label_created_no=itemView.findViewById(R.id.label_created_no);
        }
    }
    @Override
    public int getItemCount() {
        return dealerlist.size();
    }

}

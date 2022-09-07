package com.autobrix.autobrix_marketingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.DealerListPage;
import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoUpdateList;

import java.util.ArrayList;

public class AdapterdailyUpdates extends RecyclerView.Adapter<AdapterdailyUpdates.RecyclerViewHolder>
{
    String lat="0",lng="0";
    ArrayList<PojoUpdateList> list;
    Context context;
    public AdapterdailyUpdates(ArrayList<PojoUpdateList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterdailyUpdates.RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_reminder_list,parent,false);
        return  new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterdailyUpdates.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
       PojoUpdateList recyclerdata= list.get(position);
       holder.comments.setText(recyclerdata.getComments());
       holder.reminderdate.setText(recyclerdata.getReminder_time());
       holder.dealer_name.setText(recyclerdata.getDealername());
       holder.reason.setText(recyclerdata.getReason());
        if(SPHelper.getSPData(context,SPHelper.role_id,"").equals("190")){

            if(recyclerdata.getLatitude()==null || recyclerdata.getLatitude().equals("")||
                    recyclerdata.getLatitude().startsWith("0")){
                holder.iv_maps.setVisibility(View.INVISIBLE);
                holder.iv_maps_grey.setVisibility(View.VISIBLE);
            }else{
                holder.iv_maps.setVisibility(View.VISIBLE);
                holder.iv_maps_grey.setVisibility(View.INVISIBLE);
            }

        }else {
            holder.iv_maps.setVisibility(View.INVISIBLE);
            holder.iv_maps_grey.setVisibility(View.INVISIBLE);

        }
        holder.iv_maps.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               lat=recyclerdata.getLatitude();
               lng=recyclerdata.getLongitude();
               getAdress();
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder  extends RecyclerView.ViewHolder
    {
        public TextView comments,reason,dealer_name,reminderdate,remindertime;
        ImageView iv_maps,iv_maps_grey;

        public RecyclerViewHolder(@NonNull  View itemView)
        {
            super(itemView);
            reason=itemView.findViewById(R.id.reason);
            comments=itemView.findViewById(R.id.comments);
            dealer_name=itemView.findViewById(R.id.dealer_name);
            reminderdate=itemView.findViewById(R.id.reminderdate);
            remindertime=itemView.findViewById(R.id.remindertime);
            iv_maps=itemView.findViewById(R.id.iv_maps);
            iv_maps_grey=itemView.findViewById(R.id.iv_maps_grey);
        }
    }

    public  void getAdress(){
        String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        context.startActivity(intent);
    }

}

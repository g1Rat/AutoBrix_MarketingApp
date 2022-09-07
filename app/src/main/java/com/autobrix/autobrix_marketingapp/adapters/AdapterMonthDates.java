package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.pojos.PojoMngrDates;

import java.util.ArrayList;

public class AdapterMonthDates extends RecyclerView.Adapter<AdapterMonthDates.RecyclerViewHolder> {

    ArrayList<PojoMngrDates> pojoMngrDates;
    Context context;

    public AdapterMonthDates(ArrayList<PojoMngrDates> pojoMngrDates, Context context) {
        this.pojoMngrDates = pojoMngrDates;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMonthDates.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_landsacpe_month_dates,parent,false);
        return  new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMonthDates.RecyclerViewHolder holder, int position) {
        holder.date.setText(pojoMngrDates.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return pojoMngrDates.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
        }
    }
}

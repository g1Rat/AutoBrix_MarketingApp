package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.pojos.PojoDealerWiseList;

import java.util.ArrayList;

public class AdapterEmpWiseDealer extends RecyclerView.Adapter<AdapterEmpWiseDealer.RecyclerViewHolder>{

    ArrayList<PojoDealerWiseList> pojoDealerWiseLists;
    Context context;

    public AdapterEmpWiseDealer(ArrayList<PojoDealerWiseList> pojoDealerWiseLists, Context context) {
        this.pojoDealerWiseLists = pojoDealerWiseLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterEmpWiseDealer.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_landscape_dealer_wise_list,parent,
                false);
        return  new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEmpWiseDealer.RecyclerViewHolder holder, int position) {
        holder.meet_time.setText(pojoDealerWiseLists.get(position).getTime());
        holder.dealer_name.setText(pojoDealerWiseLists.get(position).getD_name());
    }

    @Override
    public int getItemCount() {
        return pojoDealerWiseLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView dealer_name,meet_time;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            meet_time=itemView.findViewById(R.id.meet_time);
            dealer_name=itemView.findViewById(R.id.dealer_name);
        }
    }
}

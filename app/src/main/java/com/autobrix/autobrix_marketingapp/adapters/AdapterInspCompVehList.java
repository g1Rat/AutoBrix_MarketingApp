package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspCompList;

import java.util.ArrayList;

public class AdapterInspCompVehList extends  RecyclerView.Adapter<AdapterInspCompVehList.RecyclerViewHolder>{

    ArrayList<PojoInspCompList> inspCompLists;
    Context context;

    public AdapterInspCompVehList(ArrayList<PojoInspCompList> inspCompLists, Context context) {
        this.inspCompLists = inspCompLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterInspCompVehList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_insp_comp_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInspCompVehList.RecyclerViewHolder holder, int position) {

        PojoInspCompList recyclerdata=inspCompLists.get(position);
        holder.insp_status.setText(recyclerdata.getStatus());
        holder.veh_no.setText(recyclerdata.getVehicle_no());

    }

    @Override
    public int getItemCount() {
        return inspCompLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView veh_no,insp_status;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            insp_status=itemView.findViewById(R.id.insp_status);
            veh_no=itemView.findViewById(R.id.veh_no);
        }
    }
}

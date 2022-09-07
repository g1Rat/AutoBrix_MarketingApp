package com.autobrix.autobrix_marketingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoMaterialStatus;

import org.json.JSONObject;

import java.util.ArrayList;

public class AdapterMaterialStatus extends RecyclerView.Adapter<AdapterMaterialStatus.RecyclerViewHolder>{
    ArrayList<PojoMaterialStatus> status_list;
    Context context;

    public AdapterMaterialStatus(ArrayList<PojoMaterialStatus> status_list, Context context) {
        this.status_list = status_list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMaterialStatus.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_material_status_list,parent,false);
        return  new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMaterialStatus.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {

        PojoMaterialStatus recyclerdata=status_list.get(position);
        holder.service_name.setText(recyclerdata.getStatus_name());
        if(recyclerdata.getIs_Selected().equals("y")){
            holder.selected.setVisibility(View.VISIBLE);
            holder.unselected.setVisibility(View.INVISIBLE);
        }else{
            holder.selected.setVisibility(View.INVISIBLE);
            holder.unselected.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                for (int i = 0; i < status_list.size(); i++) {
                    if (i == position)
                    {
                        SPHelper.status_id=status_list.get(i).getStatus_id();
                        status_list.get(i).setIs_Selected("y");
                    } else {
                        status_list.get(i).setIs_Selected("n");
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return status_list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView service_name;
        ImageView unselected,selected;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            service_name=itemView.findViewById(R.id.service_name);
            selected=itemView.findViewById(R.id.selected);
            unselected=itemView.findViewById(R.id.unselected);
        }
    }
}

package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.pojos.PojoMngrEmp;

import java.util.ArrayList;

public class AdapterMngrEmployeeList extends RecyclerView.Adapter<AdapterMngrEmployeeList.RecyclerViewHolder> {

    ArrayList<PojoMngrEmp> pojoMngrEmps;
    Context context;

    public AdapterMngrEmployeeList(ArrayList<PojoMngrEmp> pojoMngrEmps, Context context) {
        this.pojoMngrEmps = pojoMngrEmps;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMngrEmployeeList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_landscape_emp_list,parent,false);
        return  new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMngrEmployeeList.RecyclerViewHolder holder, int position) {
        holder.emp_name.setText(pojoMngrEmps.get(position).getEmp_name());
    }

    @Override
    public int getItemCount() {
        return pojoMngrEmps.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView emp_name;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            emp_name=itemView.findViewById(R.id.emp_name);
        }
    }
}

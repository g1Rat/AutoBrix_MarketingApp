package com.autobrix.autobrix_marketingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.pojos.PojoEmpTargetList;
import com.autobrix.autobrix_marketingapp.pojos.PojoSalesList;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;

public class AdapterEmpTargetList extends RecyclerView.Adapter<AdapterEmpTargetList.RecyclerViewHolder>{

    ArrayList<PojoSalesList> empTargetLists;
    Context context;

    public AdapterEmpTargetList(ArrayList<PojoSalesList> empTargetLists, Context context) {
        this.empTargetLists = empTargetLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterEmpTargetList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_emp_target_list,parent,false);
        return  new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterEmpTargetList.RecyclerViewHolder holder, int position)
    {
        holder.emp_target_count.setText("("+empTargetLists.get(position).getTarget_count()+")");
        holder.empname.setText(empTargetLists.get(position).getEmployee_name());
        //achieved sales/total target
        if(empTargetLists.get(position).getTarget().getCount()!=null)
        {
            holder.emp_pack_count.setText(empTargetLists.get(position).getTarget().getCount()+" / "+
                    empTargetLists.get(position).getTarget_count());
            double total=Double.parseDouble(empTargetLists.get(position).getTarget_count());
            double achieved=Double.parseDouble(empTargetLists.get(position).getTarget().getCount());
            int fl=(int) ((achieved/total)*100);
           // holder.emp_target_progress.setProgress((int)32.0);
            holder.emp_target_progress.setProgress(fl);
            if(achieved>total){
                holder.emp_target_progress.setIndicatorColor(Color.parseColor("#00c42e"));
            }else{
                holder.emp_target_progress.setIndicatorColor(Color.parseColor("#0619c3"));
            }
            System.out.println("target count"+(achieved/total)*100);
            //holder.emp_target_progress.show();
        }else{
            holder.emp_pack_count.setText("0"+" / "+ empTargetLists.get(position).getTarget_count());
        }
    }

    @Override
    public int getItemCount() {
        return empTargetLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView emp_target_count,empname,emp_pack_count;
        LinearProgressIndicator emp_target_progress;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            empname=itemView.findViewById(R.id.empname);
            emp_target_count=itemView.findViewById(R.id.emp_target_count);
            emp_target_progress=itemView.findViewById(R.id.emp_target_progress);
            emp_pack_count=itemView.findViewById(R.id.emp_pack_count);
        }
    }
}

package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.pojos.PojoEmpDealerList;
import com.autobrix.autobrix_marketingapp.pojos.PojoPackageList;

import java.util.ArrayList;
public class AdapterEmpDealerList extends RecyclerView.Adapter<AdapterEmpDealerList.RecyclerViewHolder>{
    ArrayList<PojoEmpDealerList> pojoEmpDealerLists;
    Context context;
    RecyclerView rv_package_list;
    ArrayList<PojoPackageList> pojoPackageLists;
    public AdapterEmpDealerList(ArrayList<PojoEmpDealerList> pojoEmpDealerLists, Context context) {
        this.pojoEmpDealerLists = pojoEmpDealerLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterEmpDealerList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_emp_dealerlist,parent,false);
        return  new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEmpDealerList.RecyclerViewHolder holder, int position) {

        holder.dealername.setText(pojoEmpDealerLists.get(position).getDealer_name());
        holder.package_count.setText(pojoEmpDealerLists.get(position).getTarget_count());


    }

    @Override
    public int getItemCount() {
        return pojoEmpDealerLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView dealername,total_package_count,package_count;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            dealername=itemView.findViewById(R.id.dealername);
            package_count=itemView.findViewById(R.id.package_count);
            rv_package_list=itemView.findViewById(R.id.rv_package_list);

        }
    }
}

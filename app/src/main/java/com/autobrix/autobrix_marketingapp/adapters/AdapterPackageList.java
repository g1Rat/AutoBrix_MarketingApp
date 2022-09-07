package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.pojos.PojoEmpDealerList;
import com.autobrix.autobrix_marketingapp.pojos.PojoPackageList;

import java.util.ArrayList;

public class AdapterPackageList extends RecyclerView.Adapter<AdapterPackageList.RecyclerViewHolder>{

    ArrayList<PojoPackageList> pojoPackageLists;
    Context context;

    public AdapterPackageList(ArrayList<PojoPackageList> pojoPackageLists, Context context) {
        this.pojoPackageLists = pojoPackageLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPackageList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_package_list,parent,false);
        return  new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPackageList.RecyclerViewHolder holder, int position) {

        holder.count.setText(pojoPackageLists.get(position).getPackages_count());
        holder.package_name.setText(pojoPackageLists.get(position).getMain_package_name());
    }

    @Override
    public int getItemCount() {
        return pojoPackageLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView package_name,count;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            package_name=itemView.findViewById(R.id.package_name);
            count=itemView.findViewById(R.id.count);
        }
    }
}

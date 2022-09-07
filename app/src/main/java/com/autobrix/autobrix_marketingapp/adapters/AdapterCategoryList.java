package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.LandscapePage;
import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.pojos.PojoCategoryList;
import com.autobrix.autobrix_marketingapp.pojos.PojoDealerWiseList;

import java.util.ArrayList;

public class AdapterCategoryList extends RecyclerView.Adapter<AdapterCategoryList.RecyclerViewHolder>{

    ArrayList<PojoCategoryList> categoryListsl;
    Context context;


    ArrayList<PojoDealerWiseList> pojoDealerWiseLists;
    RecyclerView rv_d_wise_list;
    AdapterEmpWiseDealer adapterEmpWiseDealer;
    public AdapterCategoryList(ArrayList<PojoCategoryList> categoryListsl, Context context) {
        this.categoryListsl = categoryListsl;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCategoryList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_landscape_cat_list,parent,false);
        return  new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategoryList.RecyclerViewHolder holder, int position)
    {
        holder.category_name.setText(categoryListsl.get(position).getCategory_list());
        pojoDealerWiseLists=new ArrayList<>();
        pojoDealerWiseLists.add(new PojoDealerWiseList("Sagar Cars","06:30 pm"));
        pojoDealerWiseLists.add(new PojoDealerWiseList("Sadhath Cars","10:30 am"));
        adapterEmpWiseDealer=new AdapterEmpWiseDealer(pojoDealerWiseLists,context);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_d_wise_list.setLayoutManager(layoutManager3);
        rv_d_wise_list.setAdapter(adapterEmpWiseDealer);
    }

    @Override
    public int getItemCount() {
        return categoryListsl.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView category_name;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_d_wise_list=itemView.findViewById(R.id.rv_d_wise_list);
            category_name=itemView.findViewById(R.id.category_name);
        }
    }
}

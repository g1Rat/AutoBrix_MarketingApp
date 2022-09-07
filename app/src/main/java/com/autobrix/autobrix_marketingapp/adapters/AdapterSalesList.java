package com.autobrix.autobrix_marketingapp.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.MngrSalesPage;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoEmpDealerList;
import com.autobrix.autobrix_marketingapp.pojos.PojoSalesList;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AdapterSalesList extends RecyclerView.Adapter<AdapterSalesList.RecyclerViewHolder> {
    public ArrayList<PojoSalesList> pojoSalesLists;
    public ProgressBar progress;
    public Context context;
    private ApiInterface apiInterface;
    public ArrayList<PojoEmpDealerList> pojoEmpDealerLists;
    public RelativeLayout rl_list, rl_total;
    public TextView noresults;
    public ImageView maximise,minimise;
    public RecyclerView rv_emp_dealers;
    AdapterEmpDealerList adapterEmpDealerList;
    public AdapterSalesList(ArrayList<PojoSalesList> pojoSalesLists, Context context) {
        this.pojoSalesLists = pojoSalesLists;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterSalesList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_emp_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSalesList.RecyclerViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        holder.empname.setText(pojoSalesLists.get(position).getEmployee_name());
        holder.empno.setText(pojoSalesLists.get(position).getEmployee_phone_no());
        if(pojoSalesLists.get(position).getTargetAchieved().getCount()==null){
            holder.emp_target_count.setText("0"+"/"+pojoSalesLists.get(position).getTarget_count());
        }else {
            holder.emp_target_count.setText(pojoSalesLists.get(position).getTargetAchieved().getCount()+"/"+
                    pojoSalesLists.get(position).getTarget_count());
        }


//        if(pojoSalesLists.get(position).getIs_selected().equals("y")){
//            rv_emp_dealers.setVisibility(View.VISIBLE);
//        }else {
//            rv_emp_dealers.setVisibility(View.GONE);
//        }
       pojoEmpDealerLists=new ArrayList<>();
        pojoEmpDealerLists=pojoSalesLists.get(position).getDealerList();
        //getDealerPackList();
        if(pojoEmpDealerLists.isEmpty()){
//            rl_list.setVisibility(View.VISIBLE);
//            noresults.setVisibility(View.VISIBLE);
//            rv_emp_dealers.setVisibility(View.GONE);
        }else{
//            rl_list.setVisibility(View.VISIBLE);
//            noresults.setVisibility(View.GONE);
//            rv_emp_dealers.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            rv_emp_dealers.setLayoutManager(linearLayoutManager);
            adapterEmpDealerList=new AdapterEmpDealerList(pojoEmpDealerLists,context);
            rv_emp_dealers.setAdapter(adapterEmpDealerList);
        }

//        holder.itemView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//
//                MngrSalesPage activity=(MngrSalesPage)context;
//                for (int i=0;i<pojoSalesLists.size();i++)
//                {
//                    if(i==position){
//                        pojoSalesLists.get(i).setIs_selected("y");
//                        Toast.makeText(context,pojoSalesLists.get(i).getIs_selected(),Toast.LENGTH_SHORT).show();
//                        //SPHelper.selected_eid=pojoSalesLists.get(i).getEmployee_id();
//
//                    }else {
//                        pojoSalesLists.get(i).setIs_selected("n");
//                        Toast.makeText(context,pojoSalesLists.get(i).getIs_selected(),Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//
//
//                activity.adapterSalesList.notifyDataSetChanged();
//           }
//        });


    }

    @Override
    public int getItemCount() {
        return pojoSalesLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView empname, empno, emp_target_count;
        RelativeLayout rl1;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            minimise = itemView.findViewById(R.id.minimise);
            maximise = itemView.findViewById(R.id.maximise);
            empname = itemView.findViewById(R.id.empname);
            empno = itemView.findViewById(R.id.empno);
            noresults = itemView.findViewById(R.id.noresults);
            emp_target_count = itemView.findViewById(R.id.emp_target_count);
            rv_emp_dealers = itemView.findViewById(R.id.rv_emp_dealers);
            rl_list = itemView.findViewById(R.id.rl_list);
            rl_total = itemView.findViewById(R.id.rl_total);
            rl1 = itemView.findViewById(R.id.rl1);
            progress = itemView.findViewById(R.id.progress);
        }
    }

    public  void getDealerPackList(){
        if(pojoEmpDealerLists.isEmpty()){
//            rl_list.setVisibility(View.VISIBLE);
//            noresults.setVisibility(View.VISIBLE);
//            rv_emp_dealers.setVisibility(View.GONE);
        }else{
//            rl_list.setVisibility(View.VISIBLE);
//            noresults.setVisibility(View.GONE);
//            rv_emp_dealers.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            rv_emp_dealers.setLayoutManager(linearLayoutManager);
            adapterEmpDealerList=new AdapterEmpDealerList(pojoEmpDealerLists,context);
            rv_emp_dealers.setAdapter(adapterEmpDealerList);
        }
    }
    public void getEmpDealerPackList()
    {
        if(!Connectivity.isNetworkConnected(context))
        {
            Toast.makeText(context.getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getdealerPackList("",SPHelper.selected_eid,
                    "", "","",SPHelper.monthvalue, SPHelper.yearvalue);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            pojoEmpDealerLists=new ArrayList<>();
                            pojoEmpDealerLists = appResponse.getResponseModel().getSalesDealerList();
                            rl_list.setVisibility(View.GONE);
                            rv_emp_dealers.setVisibility(View.GONE);
                            noresults.setVisibility(View.GONE);
                            if(pojoEmpDealerLists.isEmpty()){
                                rl_list.setVisibility(View.VISIBLE);
                                rv_emp_dealers.setVisibility(View.GONE);
                                noresults.setVisibility(View.VISIBLE);
                            }else
                            {
                                rv_emp_dealers.setVisibility(View.VISIBLE);
                                noresults.setVisibility(View.GONE);
                                rl_list.setVisibility(View.VISIBLE);
                                adapterEmpDealerList=new AdapterEmpDealerList(pojoEmpDealerLists, context.getApplicationContext());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                                rv_emp_dealers.setLayoutManager(layoutManager);
                                rv_emp_dealers.setAdapter(adapterEmpDealerList);
                            }
                        }
                        else
                        {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(context,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(context.getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

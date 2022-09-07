package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.DealerRequests;
import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspectedVehicleList;

import com.autobrix.autobrix_marketingapp.pojos.PojoInspectionReqList;
import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterInspectedVehicleList extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<PojoInspectedVehicleList> serviceReqLists;
    Context context;
    public ProgressBar progressBar;
    public String my_dealer_id;
    private String kdrivenfrom,kdrivento;
    private boolean isLoadingAdded = false;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private DecimalFormat IndianCurrencyFormat;
    public AdapterInspectedVehicleList(Context context) {
        this.context = context;
        serviceReqLists = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.items_insp_veh_list, parent, false);
                viewHolder = new RecyclerViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        PojoInspectedVehicleList movie = serviceReqLists.get(position);
        switch (getItemViewType(position))
        {
            case ITEM:

                final RecyclerViewHolder holder = (RecyclerViewHolder) holder1;
                PojoInspectedVehicleList recyclerdata=serviceReqLists.get(position);
                holder.tv_dealername.setText(recyclerdata.getDealer_name());
                holder.tv_dealer_no.setText(recyclerdata.getPhone_no());
                holder.tv_model.setText(recyclerdata.getCar_model());
                holder.tv_fueltype.setText(recyclerdata.getFuel_type());
                holder.tv_trans_type.setText("-"+recyclerdata.getTransmission_type());
                holder. veh_no.setText(recyclerdata.getVehicle_no());
                if(recyclerdata.getInspection_status()==null||recyclerdata.getInspection_status().equals("")){
                    holder.insp_status.setText("REVIEW");
                }else{
                    holder.insp_status.setText(recyclerdata.getInspection_status().toUpperCase(Locale.ROOT));
                }
                if(holder.insp_status.getText().equals("APPROVED")){
                    holder.insp_status.setTextColor(Color.parseColor("#008000"));
                }else  if(holder.insp_status.getText().equals("REJECTED")||holder.insp_status.getText().equals("REPAIR REQUESTED")){
                    holder.insp_status.setTextColor(Color.parseColor("#e23744"));
                }else  if(holder.insp_status.getText().equals("REINSPECT")){
                    holder.insp_status.setTextColor(Color.parseColor("#FF6739"));
                }else  {
                    holder.insp_status.setTextColor(Color.parseColor("#0619c3"));
                }
                Glide.with(context).load(recyclerdata.getBrand_logo()).placeholder(R.drawable.icon_noimage).into(holder.brand_logo);

                holder.rl_insp_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(recyclerdata.getIs_with_cooling_period()==null){

                        }
                        else if(recyclerdata.getIs_with_cooling_period().equals("y")){
                            SPHelper.comments=recyclerdata.getInspection_comments();
                            SPHelper.cool_days=recyclerdata.getCooling_period_days();
                            SPHelper.cool_kms=recyclerdata.getCooling_period_kms();
                            DealerRequests.getInstance().open_dialog();
                        }else{
                            // DealerRequests.getInstance().open_dialog();
                        }
                    }
                });
                break;
            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder1;
                progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return serviceReqLists == null ? 0 : serviceReqLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == serviceReqLists.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() throws JSONException {
        isLoadingAdded = true;
        add(new PojoInspectedVehicleList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = serviceReqLists.size() - 1;
        PojoInspectedVehicleList result = getItem(position);

        if (result != null) {
            serviceReqLists.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(PojoInspectedVehicleList movie) {
        serviceReqLists.add(movie);
        notifyItemInserted(serviceReqLists.size() - 1);
    }

    public void addAll(ArrayList<PojoInspectedVehicleList> moveResults) {
        for (PojoInspectedVehicleList result : moveResults) {
            add(result);
        }
    }

    public PojoInspectedVehicleList getItem(int position) {
        return serviceReqLists.get(position);
    }

    public class RecyclerViewHolder extends  RecyclerView.ViewHolder{
        TextView tv_dealername,tv_dealer_no,tv_model,tv_fueltype,tv_trans_type,insp_status,veh_no;
        ImageView brand_logo;
        RelativeLayout rl_insp_status;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_dealername=itemView.findViewById(R.id.tv_dealername);
            tv_dealer_no=itemView.findViewById(R.id.tv_dealer_no);
            brand_logo=itemView.findViewById(R.id.brand_logo);
            tv_model=itemView.findViewById(R.id.tv_model);
            tv_fueltype=itemView.findViewById(R.id.tv_fueltype);
            tv_trans_type=itemView.findViewById(R.id.tv_trans_type);
            insp_status=itemView.findViewById(R.id.insp_status);
            rl_insp_status=itemView.findViewById(R.id.rl_insp_status);
            veh_no=itemView.findViewById(R.id.veh_no);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder
    {


        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.itemProgressbar);
        }
    }
}

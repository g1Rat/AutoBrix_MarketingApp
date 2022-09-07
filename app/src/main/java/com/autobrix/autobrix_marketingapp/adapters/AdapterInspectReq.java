package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.DealerRequests;
import com.autobrix.autobrix_marketingapp.MngrInspList;
import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspectedVehicleList;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspectionReqList;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdapterInspectReq extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<PojoInspectionReqList> inspectionReqLists;
    Context context;
    public ProgressBar progressBar;
    public String my_dealer_id;
    private String kdrivenfrom,kdrivento;
    private boolean isLoadingAdded = false;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private DecimalFormat IndianCurrencyFormat;

    public AdapterInspectReq(Context context) {
        this.context = context;
        inspectionReqLists = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.items_inspect_req_list, parent, false);
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
       // PojoInspectionReqList movie = inspectionReqLists.get(position);
        switch (getItemViewType(position))
        {
            case ITEM:
                final RecyclerViewHolder holder = (RecyclerViewHolder) holder1;
                PojoInspectionReqList recyclerdata=inspectionReqLists.get(position);
                holder.dealer_name.setText(recyclerdata.getDealer_name());
                if(recyclerdata.getVehicle_no()==null||recyclerdata.getVehicle_no().equals("")){
                    holder.veh_no.setVisibility(View.INVISIBLE);
                }else{
                    holder.veh_no.setVisibility(View.VISIBLE);
                    holder.veh_no.setText(recyclerdata.getVehicle_no());
                }
                holder.insp_status.setText(recyclerdata.getInspection_status().toUpperCase());
                if(holder.insp_status.getText().equals("CANCELLED")){
                    holder.insp_status.setTextColor(Color.parseColor("#e23744"));
                }else  if(holder.insp_status.getText().equals("COMPLETED")||holder.insp_status.getText().equals("IN PROGRESS")){
                    holder.insp_status.setTextColor(Color.parseColor("#008000"));
                }else  if(holder.insp_status.getText().equals("RESCHEDULED")){
                    holder.insp_status.setTextColor(Color.parseColor("#FF6739"));
                }else  {
                    holder.insp_status.setTextColor(Color.parseColor("#0619c3"));
                }
                holder.req_by.setText(recyclerdata.getRequested_by());
                if(recyclerdata.getInspected_vehicle_count().equals(""))
                {
                    holder.insp_veh_count.setVisibility(View.GONE);
                    holder.veh_count.setVisibility(View.GONE);
                    //
                    // holder.veh_count.setText(recyclerdata.getNo_of_cars());
                }else if(recyclerdata.getInspected_vehicle_count().equals("0")){
                    holder.insp_veh_count.setVisibility(View.GONE);
                    holder.veh_count.setVisibility(View.VISIBLE);
                    holder.veh_count.setText(recyclerdata.getNo_of_cars());
                }
                else{
                    holder.insp_veh_count.setVisibility(View.VISIBLE);
                    holder.veh_count.setVisibility(View.GONE);
                    holder.insp_veh_count.setText(Html.fromHtml("<u>"+recyclerdata.getInspected_vehicle_count()+"/"+recyclerdata.getNo_of_cars()+"</u>"));
                }

                if(recyclerdata.getRequested_by().equals(recyclerdata.getDealer_name())){
                    holder.inspec_type.setVisibility(View.GONE);
                }else{
                    holder.inspec_type.setVisibility(View.VISIBLE);
                    holder.inspec_type.setText(recyclerdata.getInspection_type());
                }

                if(recyclerdata.getInspection_type().equalsIgnoreCase("regular inspection")){
                    holder.inspec_type.setTextColor(Color.parseColor("#0619c3"));
                }else{
                    holder.inspec_type.setTextColor(Color.parseColor("#FF6739"));
                }
                // parseDate(recyclerdata.getInspection_on_date());
                if(recyclerdata.getInspection_on_date()==null){

                }else{
                    holder.inspection_on.setText(Common.parseDate(recyclerdata.getInspection_on_date()));
                }
                if(recyclerdata.getRequested_on()==null){

                }else{
                    holder.requested_on.setText(Common.parseDate(recyclerdata.getRequested_on()));
                }

                holder.insp_veh_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPHelper.comb_req_id=recyclerdata.getInspection_combined_id();
                        if(SPHelper.getSPData(context,SPHelper.role_id,"").equals("190")){
                            MngrInspList.getInstance().open_insp_comp();
                        }else{
                            DealerRequests.getInstance().open_insp_comp();
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
        return inspectionReqLists == null ? 0 : inspectionReqLists.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return (position == inspectionReqLists.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() throws JSONException {
        isLoadingAdded = true;
        add(new PojoInspectionReqList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = inspectionReqLists.size() - 1;
        PojoInspectionReqList result = getItem(position);

        if (result != null) {
            inspectionReqLists.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(PojoInspectionReqList movie) {
        inspectionReqLists.add(movie);
        notifyItemInserted(inspectionReqLists.size() - 1);
    }
    public void addAll(ArrayList<PojoInspectionReqList> moveResults)
    {
        //moveResults=new ArrayList<>();
        for (PojoInspectionReqList result : moveResults) {
            add(result);
        }
    }
    public PojoInspectionReqList getItem(int position) {
        return inspectionReqLists.get(position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView dealer_name,veh_no,veh_count,inspection_on,label_req_by,insp_status,
                requested_on,req_by,inspec_type,insp_veh_count;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            dealer_name=itemView.findViewById(R.id.dealer_name);
            veh_no=itemView.findViewById(R.id.veh_no);
            veh_count=itemView.findViewById(R.id.veh_count);
            inspection_on=itemView.findViewById(R.id.inspection_on);
            requested_on=itemView.findViewById(R.id.requested_on);
            req_by=itemView.findViewById(R.id.req_by);
            inspec_type=itemView.findViewById(R.id.inspec_type);
            insp_status=itemView.findViewById(R.id.insp_status);
            label_req_by=itemView.findViewById(R.id.label_req_by);
            insp_veh_count=itemView.findViewById(R.id.insp_veh_count);
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

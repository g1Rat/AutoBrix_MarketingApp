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
import com.autobrix.autobrix_marketingapp.MngrDailyUpdates;
import com.autobrix.autobrix_marketingapp.MngrInspList;
import com.autobrix.autobrix_marketingapp.MngrMtrlList;
import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoSalesList;
import java.util.ArrayList;

public class AdapterMngrEmpList extends RecyclerView.Adapter<AdapterMngrEmpList.RecyclerViewHolder>{
    ArrayList<PojoSalesList> mngrEmpLists;
    Context context;

    public AdapterMngrEmpList(ArrayList<PojoSalesList> mngrEmpLists, Context context) {
        this.mngrEmpLists = mngrEmpLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMngrEmpList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_mngr_emp_list,parent,false);
        return  new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMngrEmpList.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.emp_name.setText(mngrEmpLists.get(position).getEmployee_name());
       holder.emp_no.setText(mngrEmpLists.get(position).getEmployee_phone_no());
        if(mngrEmpLists.get(position).getIs_selected().equals("y")){
            holder.iv_selected.setVisibility(View.VISIBLE);
        }else{
            holder.iv_selected.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SPHelper.emp_selected="y";
                SPHelper.selected_eid=mngrEmpLists.get(position).getEmployee_id();
                for (int i = 0; i < mngrEmpLists.size(); i++)
                {
                    if (i == position) {
                        mngrEmpLists.get(i).setIs_selected("y");
                    } else {
                        mngrEmpLists.get(i).setIs_selected("n");
                    }
                }
                if(SPHelper.mngr_came.equals("mtrl")){
                    MngrMtrlList.getInstance().tv_emp_name.setText(mngrEmpLists.get(position).getEmployee_name());
                    MngrMtrlList.getInstance().rl_select_emp.setVisibility(View.GONE);
                    MngrMtrlList.getInstance().get_All_market_mtrl_list();
                }else if(SPHelper.mngr_came.equals("insp")){
                    MngrInspList.getInstance().rl_select_emp.setVisibility(View.GONE);
                    MngrInspList.getInstance().tv_emp_name.setText(mngrEmpLists.get(position).getEmployee_name());

                        if(MngrInspList.getInstance().isInspect_clicked.equals("y")){
                            MngrInspList.getInstance().get_Inspection_ReqList();
                        }else{
                            MngrInspList.getInstance().get_insp_vehList();
                        }
                }else{
                    MngrDailyUpdates.getInstance().rl_select_emp.setVisibility(View.GONE);
                    MngrDailyUpdates.getInstance().tv_emp_name.setText(mngrEmpLists.get(position).getEmployee_name());
                    MngrDailyUpdates.getInstance().get_Daily_updateList();
                }

                //HomePage.getInstance().selecteddealer();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mngrEmpLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView emp_name,emp_no;
        ImageView iv_selected;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            emp_no=itemView.findViewById(R.id.emp_no);
            emp_name=itemView.findViewById(R.id.emp_name);
            iv_selected=itemView.findViewById(R.id.iv_selected);
        }
    }
}

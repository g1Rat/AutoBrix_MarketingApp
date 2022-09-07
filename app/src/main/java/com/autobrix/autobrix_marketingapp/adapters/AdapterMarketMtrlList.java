package com.autobrix.autobrix_marketingapp.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autobrix.autobrix_marketingapp.MarketMtrlList;
import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoMarketMtrlList;
import com.autobrix.autobrix_marketingapp.pojos.PojoMarketingMaterials;
import com.autobrix.autobrix_marketingapp.pojos.PojoMaterialRequired;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;
import com.skyhope.showmoretextview.ShowMoreTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterMarketMtrlList extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    ArrayList<PojoMarketingMaterials> list;
    Context context;
    public RecyclerView  recyclerView1;
    AdapterMaterialRequired adapterMaterialRequired;
    public Spinner mtrl_status;
    public   ArrayList<PojoMarketMtrlList> materiallist;
    int limit=3;

    public AdapterMarketMtrlList(ArrayList<PojoMarketingMaterials> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_marketing_material,parent,false);
        viewHolder = new ViewHolderOne(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position)
    {
            ViewHolderOne holder1 = (ViewHolderOne) holder;
            PojoMarketingMaterials recyclerData = list.get(position);
            holder1.dname.setText(recyclerData.getDealer_name());
            holder1.reqdate.setText(Common.parseDate(recyclerData.getRequested_on()));
            //holder1.reqdate.setText(recyclerData.getRequested_on());
            holder1.collecteddate.setText(Common.parseDate(recyclerData.getUpdated_on()));
            holder1.status.setText(recyclerData.getMaterial_status());

            if(SPHelper.getSPData(context,SPHelper.role_id,"").equals("190")){
                holder1.menu.setVisibility(View.INVISIBLE);
            }else{
                holder1.menu.setVisibility(View.VISIBLE);
            }

            if(recyclerData.getComments()==null||recyclerData.getComments().equals("")){
                holder1.status_comments.setVisibility(View.GONE);
                holder1.comments_label.setVisibility(View.GONE);
            }else{
                holder1.status_comments.setVisibility(View.VISIBLE);
                holder1.comments_label.setVisibility(View.VISIBLE);
                holder1.status_comments.setText(recyclerData.getComments());
                if(recyclerData.getComments().length()>40){
                    holder1.status_comments.setText(recyclerData.getComments());
                    holder1.status_comments.setShowingChar(41);
                    holder1.status_comments.addShowMoreText("Continue");
                    holder1.status_comments.setShowingLine(3);
                    holder1.status_comments.setShowMoreColor(Color.RED); // or other color
                    holder1.status_comments.setShowLessTextColor(Color.BLUE);
                }
            }
            if(recyclerData.getMaterial_status()==null){
                holder1.status.setText("");
            }
            else if(recyclerData.getMaterial_status().equalsIgnoreCase("Approved") ||
                recyclerData.getMaterial_status().equalsIgnoreCase("design approved")||
                recyclerData.getMaterial_status().equalsIgnoreCase("collected")){
                holder1.status.setTextColor(Color.parseColor("#008000"));
            }
            else if(recyclerData.getMaterial_status().equalsIgnoreCase("request cancelled")){
                holder1.status.setTextColor(Color.parseColor("#FF0000"));
            }else if(recyclerData.getMaterial_status().equalsIgnoreCase("delivered to executive")
            ||recyclerData.getMaterial_status().equalsIgnoreCase("sent to dealer")){
                holder1.status.setTextColor(Color.parseColor("#FF6739"));
            }else{
                holder1.status.setTextColor(Color.parseColor("#0619c3"));
            }
            //get_market_mtrl_list();

            materiallist=new ArrayList();
        if(SPHelper.mngr_came.equals("mtrl")){
            materiallist=list.get(position).getManagerMaterialDetails();
        }else{
            materiallist=list.get(position).getMaterialList();
        }

            if(materiallist.isEmpty()){

            }else {
                adapterMaterialRequired = new AdapterMaterialRequired(materiallist, context.getApplicationContext());
                LinearLayoutManager layoutManager2 = new LinearLayoutManager(context.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView1.setLayoutManager(layoutManager2);
                recyclerView1.setAdapter(adapterMaterialRequired);
                //notifyDataSetChanged();
            }


           //        if(materiallist.size()<=limit)
//        {
//            holder1.view_more.setVisibility(View.GONE);
//            SPHelper.more_visible="n";
//        }
//        else
//        {
//            holder1.view_more.setVisibility(View.VISIBLE);
//            SPHelper.more_visible="y";
//        }
//
//        holder1.view_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SPHelper.more_visible="n";
//                holder1.view_less.setVisibility(View.VISIBLE);
//                holder1.view_more.setVisibility(View.GONE);
//                adapterMaterialRequired.notifyDataSetChanged();
//            }
//        });
//        holder1.view_less.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SPHelper.more_visible="y";
//                holder1.view_less.setVisibility(View.GONE);
//                holder1.view_more.setVisibility(View.VISIBLE);
//                adapterMaterialRequired.notifyDataSetChanged();
//            }
//        });
            holder1.rl_statusmenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(SPHelper.mngr_came.equals("mtrl")){

                    }else{
                        SPHelper.request_id=recyclerData.getRequest_id();
                        SPHelper.status_id="";
                        MarketMtrlList.getInstance().open();
                    }
                }
            });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView dname,reqdate,collecteddate,view_more,view_less,status,comments_label;
        ShowMoreTextView status_comments;
        ImageView menu;
        RelativeLayout rl_statusmenu;

        public ViewHolderOne(View itemView) {
            super(itemView);
            dname=itemView.findViewById(R.id.dname);
            reqdate=itemView.findViewById(R.id.reqdate);
            collecteddate=itemView.findViewById(R.id.collecteddate);
            recyclerView1=itemView.findViewById(R.id.rv2);
            view_more=itemView.findViewById(R.id.view_more);
            view_less=itemView.findViewById(R.id.view_less);
            mtrl_status=itemView.findViewById(R.id.mtrl_status);
            status_comments=itemView.findViewById(R.id.status_comments);
            status=itemView.findViewById(R.id.status);
            rl_statusmenu=itemView.findViewById(R.id.rl_statusmenu);
            menu=itemView.findViewById(R.id.menu);
            comments_label=itemView.findViewById(R.id.comments_label);
        }
    }
}

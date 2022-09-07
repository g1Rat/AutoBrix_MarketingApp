package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoEmpDealerList;
import com.autobrix.autobrix_marketingapp.pojos.PojoSalesList;
import java.util.ArrayList;

public class AdapterExpandable extends BaseExpandableListAdapter {
    TextView empname,empno,emp_target_count;
    RelativeLayout rl1;
    public AdapterEmpDealerList adapterEmpDealerList;
    RecyclerView rv_emp_dealers;
    public RelativeLayout rl_list,rl_total;
    public TextView noresults;
    public ImageView maximise,minimise;
    RecyclerView rv_package_list;
    TextView dealername,total_package_count,package_count;
    ArrayList<PojoSalesList> pojoSalesLists;
    Context context;
    ArrayList<PojoEmpDealerList> pojoEmpDealerLists;

    public AdapterExpandable( Context context,ArrayList<PojoSalesList> pojoSalesLists,
                             ArrayList<PojoEmpDealerList> pojoEmpDealerLists) {
        this.context = context;
        this.pojoSalesLists = pojoSalesLists;
        this.pojoEmpDealerLists = pojoEmpDealerLists;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.pojoSalesLists.get(listPosition).getDealerList();
    }
    @Override
    public int getGroupCount() {
        return this.pojoSalesLists.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.pojoEmpDealerLists.size();
    }

    @Override
    public Object getGroup(int i) {
        return this.pojoSalesLists.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View itemView, ViewGroup parent)
    {
//        String listTitle = (String) getGroup(listPosition);
        if (itemView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.items_emp_list, null);
        }

        minimise=itemView.findViewById(R.id.minimise);
        maximise=itemView.findViewById(R.id.maximise);
        empname=itemView.findViewById(R.id.empname);
        empno=itemView.findViewById(R.id.empno);
        noresults=itemView.findViewById(R.id.noresults);
        emp_target_count=itemView.findViewById(R.id.emp_target_count);
        rv_emp_dealers=itemView.findViewById(R.id.rv_emp_dealers);
        rl_list=itemView.findViewById(R.id.rl_list);
        rl_total=itemView.findViewById(R.id.rl_total);
        rl1=itemView.findViewById(R.id.rl1);

        empname.setText(pojoSalesLists.get(listPosition).getEmployee_name());
        empno.setText(pojoSalesLists.get(listPosition).getEmployee_phone_no());
        emp_target_count.setText(pojoSalesLists.get(listPosition).getTarget_count());
        pojoEmpDealerLists=new ArrayList<>();
        pojoEmpDealerLists = pojoSalesLists.get(listPosition).getDealerList();
        SPHelper.selected_eid=pojoSalesLists.get(listPosition).getEmployee_id();
//        if(pojoEmpDealerLists.isEmpty()){
//            rl_list.setVisibility(View.GONE);
//            noresults.setVisibility(View.VISIBLE);
//        }else
//        {
//            noresults.setVisibility(View.GONE);
//            rl_list.setVisibility(View.VISIBLE);
//            adapterEmpDealerList=new AdapterEmpDealerList(pojoEmpDealerLists, context.getApplicationContext());
//            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//            rv_emp_dealers.setLayoutManager(layoutManager);
//            rv_emp_dealers.setAdapter(adapterEmpDealerList);
//
//        }
        return itemView;
    }

    @Override
    public View getChildView(int position, int i1, boolean b, View itemView, ViewGroup viewGroup) {

        final String expandedListText = (String) getChild(position, i1);
        if (itemView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.items_emp_dealerlist, null);
        }
        dealername=itemView.findViewById(R.id.dealername);
        package_count=itemView.findViewById(R.id.package_count);
        rv_package_list=itemView.findViewById(R.id.rv_package_list);

        dealername.setText(pojoEmpDealerLists.get(position).getDealer_name());
        package_count.setText(pojoEmpDealerLists.get(position).getTarget_count());
        //expandedListTextView.setText(expandedListText);
        return itemView;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

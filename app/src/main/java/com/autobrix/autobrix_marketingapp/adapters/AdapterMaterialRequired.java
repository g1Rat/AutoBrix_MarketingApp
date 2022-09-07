package com.autobrix.autobrix_marketingapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoMarketMtrlList;
import com.autobrix.autobrix_marketingapp.pojos.PojoMarketingMaterials;
import com.autobrix.autobrix_marketingapp.pojos.PojoMaterialRequired;
import com.skyhope.showmoretextview.ShowMoreTextView;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class AdapterMaterialRequired extends RecyclerView.Adapter<AdapterMaterialRequired.RecyclerViewHolder>
{
    ArrayList<PojoMarketMtrlList> materiallist;
    Context context;
   AdapterMaterialRequired adapterMaterialRequired;
   String limit="4";

    public AdapterMaterialRequired(ArrayList<PojoMarketMtrlList> materiallist, Context context) {
        this.materiallist = materiallist;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterMaterialRequired.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_material_required,parent,false);
        return  new AdapterMaterialRequired.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterMaterialRequired.RecyclerViewHolder holder, int position)
    {
        PojoMarketMtrlList recyclerData = materiallist.get(position);
        holder.mtrl1.setText(recyclerData.getMaterial_name());
        holder.count_mtrl1.setText(recyclerData.getQuantity());
        //set comments according to
        if(SPHelper.onstandard.equals("y")){
            holder.comments.setVisibility(View.GONE);
            holder.view_more.setVisibility(View.GONE);
            holder.view_less.setVisibility(View.GONE);

        }
        else
        {
            if(recyclerData.getComments()==null||recyclerData.getComments().equals("")){
                holder.comments.setVisibility(View.GONE);
            }
            else{
                holder.comments.setVisibility(View.VISIBLE);
                holder.comments.setText(recyclerData.getComments());
                if(recyclerData.getComments().length()>25){
                    holder.comments.setShowingChar(26);
                    holder.comments.addShowMoreText("Continue");
                    holder.comments.setShowingLine(3);
                    holder.comments.setShowMoreColor(Color.RED); // or other color
                    holder.comments.setShowLessTextColor(Color.BLUE);
                }
            }
        }



        //You have to use following one of method

        // For using character length


//        holder.view_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.view_less.setVisibility(View.VISIBLE);
//                holder.view_more.setVisibility(View.GONE);
//                holder.comments.setText(recyclerData.getComments());
//                holder.comments.setMovementMethod(LinkMovementMethod.getInstance());
//                holder.comments.setText(Html.fromHtml(recyclerData.getComments()+"<font color='red'> <u>View More</u></font>"));
//
//            }
//        });
//        holder.view_less.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.view_more.setVisibility(View.VISIBLE);
//                holder.view_less.setVisibility(View.GONE);
//                holder.comments.setText(recyclerData.getComments());
//                holder.comments.setMaxLines(1);
//            }
//        });
    }

    @Override
    public int getItemCount()
    {
        //if viewmore is visible return 3
        //if viw less is visible thn return  return materiallist.size();
//        if(SPHelper.more_visible.equals("y"))
//        {
//            return  3;
//        }else{
//            return materiallist.size();
//        }
        return materiallist.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        public  TextView mtrl1,count_mtrl1,view_more,view_less;
        ShowMoreTextView comments;
        public RecyclerViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            mtrl1=itemView.findViewById(R.id.mtrl1);
            count_mtrl1=itemView.findViewById(R.id.count_mtrl1);
            comments=itemView.findViewById(R.id.comments);
            view_more=itemView.findViewById(R.id.view_more);
            view_less=itemView.findViewById(R.id.view_less);
        }
    }
}

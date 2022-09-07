package com.autobrix.autobrix_marketingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.autobrix.autobrix_marketingapp.R;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddMaterial;
import com.autobrix.autobrix_marketingapp.pojos.PojoMaterialRequired;
import java.util.ArrayList;

public class AdapterAddMtrl extends RecyclerView.Adapter<AdapterAddMtrl.RecyclerViewHolder>{
    ArrayList<PojoMaterialRequired> addMaterials;
    Context context;
    public  EditText count_mtrl,comments;
    Boolean isonTextchanged=false,iscommentchanged=false;

    public AdapterAddMtrl(ArrayList<PojoMaterialRequired> addMaterials, Context context) {
        this.addMaterials = addMaterials;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterAddMtrl.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_add_mtrl_list,parent,false);
        return  new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddMtrl.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {

        PojoMaterialRequired recyclerdata=addMaterials.get(position);
        holder.mtrl.setText(recyclerdata.getMaterial_name());
        if(SPHelper.onstandard.equals("y")){
            comments.setVisibility(View.GONE);
        }else {
            comments.setVisibility(View.VISIBLE);
        }

        String id = recyclerdata.getId();
        count_mtrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isonTextchanged = true;

            }
            @Override
            public void afterTextChanged(Editable editable) {
                // recyclerdata.setTotal_count(editable.toString());
                if (isonTextchanged)
                {
                    isonTextchanged = false;
                    for (int i = 0; i <= addMaterials.size(); i++)
                    {
                        if (i == position) {
                            addMaterials.get(position).setTotal_count("");

                        }else{
                            addMaterials.get(position).setTotal_count(editable.toString());
                        }
                    }
                }
            }
        });
        comments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                iscommentchanged = true;

            }
            @Override
            public void afterTextChanged(Editable editable) {

                if (iscommentchanged)
                {
                    iscommentchanged = false;
                    for (int i = 0; i <= addMaterials.size(); i++)
                    {
                        if (i == position) {
                            addMaterials.get(position).setSelected_comment("");

                        }else{
                            addMaterials.get(position).setSelected_comment(editable.toString());
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return addMaterials.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView mtrl;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mtrl=itemView.findViewById(R.id.mtrl);
            comments=itemView.findViewById(R.id.comments);
            count_mtrl=itemView.findViewById(R.id.count_mtrl);
        }
    }
}

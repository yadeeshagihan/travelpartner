package com.tugaybakay.travelpartner.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tugaybakay.travelpartner.Constans.MyConstans;
import com.tugaybakay.travelpartner.Database.ItemDb;
import com.tugaybakay.travelpartner.Models.Item;
import com.tugaybakay.travelpartner.R;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {
    List<Item> list;
    Context context;
    ItemDb db;
    String show;

    public CheckListAdapter(List<Item> list, Context context, ItemDb db, String show) {
        this.list = list;
        this.context = context;
        this.db = db;
        this.show = show;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.check_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.checkBox.setText(list.get(position).getItemName());
        holder.checkBox.setChecked(list.get(position).isChecked());
        if(MyConstans.FALSE_STRING.matches(show)){
            holder.button.setVisibility(View.GONE);
            holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border));
        }else{
            if(list.get(position).isChecked()){
                holder.layout.setBackgroundColor(Color.parseColor("#8e546f"));
            }else{
                holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border));
            }
         }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = holder.checkBox.isChecked();

                db.getDao().checkUncheck(list.get(position).getID(),check);
                if(MyConstans.FALSE_STRING.matches(show)){
                    list = db.getDao().getAllChecked(true);
                    notifyDataSetChanged();
                }else{
                    list.get(position).setChecked(check);
                    notifyDataSetChanged();
                }
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Delete "+list.get(position).getItemName())
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.getDao().delete(list.get(position));
                                list.remove(list.get(position));
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(R.drawable.baseline_delete_24).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        CheckBox checkBox;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.linearLayout);
            checkBox = itemView.findViewById(R.id.checkbox);
            button = itemView.findViewById(R.id.itemButton);

        }
    }
}
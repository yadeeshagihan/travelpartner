package com.tugaybakay.travelpartner.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tugaybakay.travelpartner.CheckList;
import com.tugaybakay.travelpartner.Constans.MyConstans;
import com.tugaybakay.travelpartner.databinding.RecyclerRowBinding;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder >{

    List<String> titles;
    List<Integer> images;

    public Adapter(List<String> titles, List<Integer> images) {
        this.titles = titles;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.title.setText(titles.get(position));
        holder.binding.img.setImageResource(images.get(position));
        holder.binding.linearL.setAlpha(0.92f);
        holder.binding.linearL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"Basıldı!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), CheckList.class);
                intent.putExtra(MyConstans.HEADER_SMALL,titles.get(position));
                if(MyConstans.MY_SELECTIONS.matches(titles.get(position))){
                    intent.putExtra(MyConstans.SHOW_SMALL,MyConstans.FALSE_STRING);
                }else{
                    intent.putExtra(MyConstans.SHOW_SMALL,MyConstans.TRUE_STRING);
                }
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerRowBinding binding;
        public ViewHolder(@NonNull RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

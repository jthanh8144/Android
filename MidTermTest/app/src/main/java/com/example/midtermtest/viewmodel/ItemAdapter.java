package com.example.midtermtest.viewmodel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermtest.R;
import com.example.midtermtest.databinding.ItemBinding;
import com.example.midtermtest.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Item> items;

    public ItemAdapter(ArrayList<Item> dataSet) {
        items = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item1, parent, false);
//        return new ViewHolder(view);

        ItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item,
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        holder.binding.setItem(items.get(position));
        holder.binding.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG0", items.get(holder.getAdapterPosition()).getName());
            }
        });
//        Item item = items.get(position);
//        holder.tvName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemBinding binding;

        public ViewHolder(final ItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }

//        public TextView tvName;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvName = itemView.findViewById(R.id.tv_name);
//        }
    }

    public void setItems(List<Item> listItems) {
        this.items.clear();
        this.items.addAll(listItems);
        notifyDataSetChanged();
    }
}

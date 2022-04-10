package com.midterm.vovanthanh.viewmodel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.midterm.vovanthanh.R;
import com.midterm.vovanthanh.databinding.ItemBinding;
import com.midterm.vovanthanh.model.Item;

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
        ItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        holder.binding.setItem(items.get(position));
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = items.get(holder.getAdapterPosition());
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                Navigation.findNavController(view).navigate(R.id.detailFragment, bundle);
            }
        });
        holder.binding.llMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle("Xóa item");
                dialog.setMessage("Bạn có muốn xóa item này không?");
                dialog.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AppDatabase appDatabase = AppDatabase.getInstance(view.getContext());
                        ItemDao itemDao = appDatabase.itemDao();
                        Item item = items.get(holder.getAdapterPosition());
                        itemDao.delete(item);
                        setItems(itemDao.getAll());
                    }
                });
                dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
                return false;
            }
        });
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
    }

    public void setItems(List<Item> listItems) {
        this.items.clear();
        this.items.addAll(listItems);
        notifyDataSetChanged();
    }

    public void findByTitle(List<Item> listItems, String name) {
        this.items.clear();
        if (name.isEmpty()) {
            this.items.addAll(listItems);
        } else {
            name = name.toLowerCase();
            for (Item item : listItems) {
                if (item.getTitle().toLowerCase().contains(name)) {
                    this.items.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}

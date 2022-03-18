package com.example.dogapp.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<DogBreed> dogBreeds;

    public ItemAdapter(ArrayList<DogBreed> dataSet) {
        dogBreeds = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DogBreed dogBreed = dogBreeds.get(position);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Drawable drawable = Drawable.createFromStream((InputStream) new URL(dogBreed.getUrl()).getContent(), "src");
                    holder.imgMain.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.imgMain.setImageDrawable(drawable);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        holder.tvName.setText(dogBreed.getName());
        holder.tvBredFor.setText(dogBreed.getBredFor());
    }

    @Override
    public int getItemCount() {
        return dogBreeds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgMain;
        public TextView tvName;
        public TextView tvBredFor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMain = itemView.findViewById(R.id.img_main);
            tvName = itemView.findViewById(R.id.tv_name);
            tvBredFor = itemView.findViewById(R.id.tv_bred_for);
        }
    }
}

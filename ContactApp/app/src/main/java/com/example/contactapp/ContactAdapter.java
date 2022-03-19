package com.example.contactapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private ArrayList<Contact> contacts;
    private Context context;
    private OnItemClickListener listener;

    public ContactAdapter(ArrayList<Contact> dataSet, Context context) {
        this.context = context;
        this.contacts = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.tvName.setText(contact.getLastName() + " " + contact.getFirstName());
        if (contact.getAvatar() != null) {
            holder.ivAvatar.setImageBitmap(BitmapHelper.byteArrayToBitmap(contact.getAvatar()));
        } else {
            holder.ivAvatar.setImageResource(
                    context.getResources().getIdentifier("ic_baseline_person_24", "drawable", context.getPackageName()));
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageView ivAvatar;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_fullname);
            ivAvatar = view.findViewById(R.id.iv_avatar);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    if (listener != null && index != RecyclerView.NO_POSITION) {
                        listener.onItemClick(contacts.get(index));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setContacts(List<Contact> listContacts) {
        this.contacts.clear();
        this.contacts.addAll(listContacts);
        notifyDataSetChanged();
    }

    public void findByName(List<Contact> listContacts, String name) {
        this.contacts.clear();
        if (name.isEmpty()) {
            this.contacts.addAll(listContacts);
        } else {
            name = name.toLowerCase();
            for (Contact contact : listContacts) {
                String fullName = contact.getLastName() + " " + contact.getFirstName();
                if (fullName.toLowerCase().contains(name)) {
                    this.contacts.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }
}

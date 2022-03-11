package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.contactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Contact> contacts;
    private ContactAdapter contactAdapter;

    private AppDatabase appDatabase;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        appDatabase = AppDatabase.getInstance(this);
//        contactDao = appDatabase.contactDao();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Contact a = new Contact("Nguyen Van A", "0123", "a@gmail.com");
                contactDao.insertAll(a);
            }
        });

        contacts = new ArrayList<>();

        contactAdapter = new ContactAdapter(contacts);
        binding.rvContacts.setAdapter(contactAdapter);
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }
}
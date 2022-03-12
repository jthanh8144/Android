package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.contactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Contact> contacts;
    private ContactAdapter contactAdapter;

    private AppDatabase appDatabase;
    private ContactDao contactDao;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm trong danh bạ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                Contact a = new Contact("Nguyen Van A", "0123", "a@gmail.com");
//                contactDao.insertAll(a);
//            }
//        });

        contacts = new ArrayList<>();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                contacts.addAll(contactDao.getAll());
            }
        });

        contactAdapter = new ContactAdapter(contacts);
        binding.rvContacts.setAdapter(contactAdapter);
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
}
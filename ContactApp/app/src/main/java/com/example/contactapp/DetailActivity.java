package com.example.contactapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.contactapp.databinding.ActivityDetailBinding;

import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private AppDatabase appDatabase;
    private ContactDao contactDao;
    private int id;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_edit:
                ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == Activity.RESULT_OK) {
                                    Intent data = result.getData();
                                    String name = data.getStringExtra("name");
                                    String phone = data.getStringExtra("phone");
                                    String email = data.getStringExtra("email");

                                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            contactDao.update(id, name, phone, email);
                                        }
                                    });
                                    binding.tvName.setText(name);
                                    binding.tvPhone.setText(phone);
                                    Toast.makeText(getApplicationContext(), "Chỉnh sửa liên hệ thành công!!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
                Intent intent = new Intent(DetailActivity.this, AddActivity.class);
                Contact contact = contactDao.findById(id);
                intent.putExtra("name", contact.getName());
                intent.putExtra("phone", contact.getMobile());
                intent.putExtra("email", contact.getEmail());
                resultLauncher.launch(intent);
                break;
            case R.id.btn_delete:
                break;
            case R.id.btn_mark:
                break;
            default:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id", -1);
        }
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Contact contact = contactDao.findById(id);
                binding.tvName.setText(contact.getName());
                binding.tvPhone.setText(contact.getMobile());
            }
        });
    }
}
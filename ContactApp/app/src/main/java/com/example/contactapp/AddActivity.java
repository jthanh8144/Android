package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.contactapp.databinding.ActivityAddBinding;

import java.util.concurrent.Executors;

public class AddActivity extends AppCompatActivity {
    private ActivityAddBinding binding;
    private boolean isUpdate = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_save:
                String name = binding.etName.getText().toString();
                String phone = binding.etPhone.getText().toString();
                String email = binding.etEmail.getText().toString();
                if (name.isEmpty() == false && phone.isEmpty() == false) {
//                    Intent intent = new Intent();
//                    intent.putExtra("name", name);
//                    intent.putExtra("phone", phone);
//                    intent.putExtra("email", email);
//                    setResult(RESULT_OK, intent);
//                    finish();
                    Intent intent;
                    if (isUpdate == false) {
                        intent = new Intent(AddActivity.this, MainActivity.class);
                    } else {
                        intent = new Intent(AddActivity.this, DetailActivity.class);
                    }
                    intent.putExtra("name", name);
                    intent.putExtra("phone", phone);
                    intent.putExtra("email", email);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, "Vui lòng nhập tên và số điện thoại", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if (intent != null) {
            setTitle("Chỉnh sửa liên hệ");
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    binding.etName.setText(intent.getStringExtra("name"));
                    binding.etPhone.setText(intent.getStringExtra("phone"));
                    binding.etEmail.setText(intent.getStringExtra("email"));
                }
            });
        }
    }
}
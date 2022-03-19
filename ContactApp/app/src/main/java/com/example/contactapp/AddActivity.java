package com.example.contactapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.contactapp.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    private ActivityAddBinding binding;
    private Uri selectedImage;
    private Bitmap avatar;

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
                String firstName = binding.etFirstName.getText().toString();
                String lastName = binding.etLastName.getText().toString();
                String phone = binding.etPhone.getText().toString();
                String email = binding.etEmail.getText().toString();
                if (firstName.isEmpty() == false && phone.isEmpty() == false) {
                    Intent intent = new Intent();
                    if (selectedImage == null) {
                        intent.putExtra("avatar", "");
                    } else {
                        intent.putExtra("avatar", selectedImage.toString());
                    }
                    intent.putExtra("firstName", firstName);
                    intent.putExtra("lastName", lastName);
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

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        selectedImage = result.getData().getData();
                        binding.imgAvatar.setImageURI(selectedImage);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                resultLauncher.launch(intent);
            }
        });
    }
}
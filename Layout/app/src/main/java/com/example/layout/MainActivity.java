package com.example.layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import com.example.layout.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ListViewModel listViewModel;
    private EditTextModel editTextModelA;
    private EditTextModel editTextModelB;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                arrayList);
        binding.lvHistory.setAdapter(arrayAdapter);
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        listViewModel.getHistories().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                if (arrayList.equals(strings) == false) {
                    arrayList.clear();
                    for (int i = 0; i < strings.size(); i++) {
                        arrayList.add(strings.get(i));
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        editTextModelA = new ViewModelProvider(this).get(EditTextModel.class);
        editTextModelA.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.edtA.setText(s);
            }
        });

        editTextModelB = new ViewModelProvider(this).get(EditTextModel.class);
        editTextModelB.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.edtB.setText(s);
            }
        });

        binding.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = binding.edtA.getText().toString();
                String b = binding.edtA.getText().toString();
                if (a.equals("") == false && b.equals("") == false) {
                    listViewModel.addHistory(a + " + " + b + " = " + (Integer.parseInt(a) + Integer.parseInt(b)));
                }
                closeKeyboard();
            }
        });

        binding.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = binding.edtA.getText().toString();
                String b = binding.edtA.getText().toString();
                if (a.equals("") == false && b.equals("") == false) {
                    listViewModel.addHistory(a + " - " + b + " = " + (Integer.parseInt(a) - Integer.parseInt(b)));
                }
                closeKeyboard();
            }
        });

        binding.btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = binding.edtA.getText().toString();
                String b = binding.edtA.getText().toString();
                if (a.equals("") == false && b.equals("") == false) {
                    listViewModel.addHistory(a + " * " + b + " = " + (Integer.parseInt(a) * Integer.parseInt(b)));
                }
                closeKeyboard();
            }
        });

        binding.btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = binding.edtA.getText().toString();
                String b = binding.edtA.getText().toString();
                if (a.equals("") == false && b.equals("") == false) {
                    listViewModel.addHistory(a + " / " + b + " = " + (Integer.parseInt(a) / Integer.parseInt(b)));
                }
                closeKeyboard();
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
package com.example.midtermtest.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.midtermtest.R;
import com.example.midtermtest.model.Item;
import com.example.midtermtest.viewmodel.ApiService;
import com.example.midtermtest.viewmodel.ItemAdapter;
import com.example.midtermtest.viewmodel.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class mainFragment extends Fragment {
    private ArrayList<Item> items;
    private RecyclerView rvMain;
    private ApiService apiService;
    private ItemAdapter itemAdapter;
    private ItemViewModel viewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvMain =view.findViewById(R.id.rv_main);
        items = new ArrayList<Item>();
        itemAdapter = new ItemAdapter(items);
        rvMain.setAdapter(itemAdapter);
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getListItems().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                itemAdapter.setItems(items);
            }
        });
        for (Item item : items) {
            Log.d("DEBUG0", item.getName());
        }

//        apiService = ApiService.getInstance(getContext());
//        apiService.getItems()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<Item>>() {
//                    @Override
//                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Item> listItems) {
//                        for (Item item : listItems) {
//                            Log.d("DEBUG1", item.getName());
//                        }
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                        Log.d("DEBUG1", e.getMessage());
//                    }
//                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
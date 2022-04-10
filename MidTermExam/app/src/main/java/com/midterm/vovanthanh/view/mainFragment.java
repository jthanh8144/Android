package com.midterm.vovanthanh.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.midterm.vovanthanh.R;
import com.midterm.vovanthanh.model.Item;
import com.midterm.vovanthanh.viewmodel.ApiService;
import com.midterm.vovanthanh.viewmodel.AppDatabase;
import com.midterm.vovanthanh.viewmodel.ItemAdapter;
import com.midterm.vovanthanh.viewmodel.ItemDao;
import com.midterm.vovanthanh.viewmodel.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class mainFragment extends Fragment {
    private ArrayList<Item> items;
    private ArrayList<Item> findList;
    private RecyclerView rvMain;
    private ItemAdapter itemAdapter;
    private ItemViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onCreateOptionsMenu(@androidx.annotation.NonNull Menu menu, @androidx.annotation.NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemAdapter.findByTitle(findList, newText);
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvMain = view.findViewById(R.id.rv_main);
        items = new ArrayList<Item>();
        findList = new ArrayList<Item>();
        itemAdapter = new ItemAdapter(items);
        rvMain.setAdapter(itemAdapter);
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getListItems().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> list) {
                itemAdapter.setItems(list);
            }
        });
        AppDatabase appDatabase = AppDatabase.getInstance(getContext());
        ItemDao itemDao = appDatabase.itemDao();
        findList.addAll(itemDao.getAll());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
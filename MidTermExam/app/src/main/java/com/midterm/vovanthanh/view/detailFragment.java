package com.midterm.vovanthanh.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.midterm.vovanthanh.R;
import com.midterm.vovanthanh.databinding.FragmentDetailBinding;
import com.midterm.vovanthanh.model.Item;

public class detailFragment extends Fragment {
    private Item item;
    private FragmentDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = (Item) getArguments().getSerializable("item");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_detail, null, false);
        binding.setItem(item);
        return binding.getRoot();
    }
}
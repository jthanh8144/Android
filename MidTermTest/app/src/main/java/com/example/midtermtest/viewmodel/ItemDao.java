package com.example.midtermtest.viewmodel;

import androidx.room.*;

import com.example.midtermtest.model.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM Item")
    List<Item> getAll();

    @Query("SELECT * FROM Item WHERE id = :id")
    List<Item> findById(int id);

    @Insert
    void insertAll(Item... items);
}

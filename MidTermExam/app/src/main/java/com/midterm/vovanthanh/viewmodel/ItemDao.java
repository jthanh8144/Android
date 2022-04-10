package com.midterm.vovanthanh.viewmodel;


import androidx.room.*;

import com.midterm.vovanthanh.model.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM Item")
    List<Item> getAll();

    @Insert
    void insertAll(Item... items);

    @Delete
    void delete(Item item);
}

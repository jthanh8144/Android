package com.example.contactapp;

import androidx.room.*;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact ORDER BY name")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact WHERE name LIKE '%' || :name || '%' ORDER BY name")
    List<Contact> findByName(String name);

    @Insert
    void insertAll(Contact... contacts);

    @Delete
    void delete(Contact contact);
}

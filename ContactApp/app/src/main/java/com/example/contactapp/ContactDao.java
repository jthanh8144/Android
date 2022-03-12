package com.example.contactapp;

import androidx.room.*;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    List<Contact> getAll();

    @Insert
    void insertAll(Contact... contacts);

    @Delete
    void delete(Contact contact);
}

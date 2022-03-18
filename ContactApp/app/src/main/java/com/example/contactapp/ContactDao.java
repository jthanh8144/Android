package com.example.contactapp;

import androidx.room.*;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact ORDER BY name")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact WHERE name LIKE '%' || :name || '%' ORDER BY name")
    List<Contact> findByName(String name);

    @Query("SELECT * FROM Contact WHERE id = :id")
    Contact findById(int id);

    @Insert
    void insertAll(Contact... contacts);

    @Query("UPDATE Contact SET name = :name, mobile = :mobile, email = :email WHERE id = :id")
    void update(int id, String name, String mobile, String email);

    @Delete
    void delete(Contact contact);
}

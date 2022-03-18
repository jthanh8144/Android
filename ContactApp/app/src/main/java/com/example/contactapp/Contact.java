package com.example.contactapp;

import androidx.room.*;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String mobile;

    @ColumnInfo
    private String email;

    @ColumnInfo
    private boolean mark;

    public Contact(String name, String mobile, String email) {
//        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.mark = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public boolean isMark() {
        return mark;
    }
}

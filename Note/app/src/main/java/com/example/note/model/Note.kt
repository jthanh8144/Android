package com.example.note.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo
    val title: String,

    @ColumnInfo
    val description: String,

    @ColumnInfo
    val entryDate: String,

//    @ColumnInfo
//    val entryDate: Date = Calendar.getInstance().time
) {
    init {

    }
}

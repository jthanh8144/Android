package com.example.note.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.example.note.model.Note
import java.util.*

@Database(entities = [Note::class], version = 1, exportSchema = false)
//@TypeConverter()
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}
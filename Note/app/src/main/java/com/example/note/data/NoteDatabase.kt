package com.example.note.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.note.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao

//    companion object {
//        private var dbInstance: NoteDatabase ?= null
//
//        fun getNoteDB(context: Context): NoteDatabase {
//            if (dbInstance == null) {
//                dbInstance = Room
//                    .databaseBuilder(context, NoteDatabase::class.java, "notes")
//                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
//                    .build()
//            }
//            return dbInstance!!
//        }
//    }
}

package com.example.note.di

import android.content.Context
import androidx.room.Room
import com.example.note.data.NoteDatabase
import com.example.note.data.NoteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideNotesDao(noteDatabase: NoteDatabase): NoteDatabaseDao {
        return noteDatabase.noteDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : NoteDatabase {
//        return NoteDatabase.getNoteDB(context)
        return Room
            .databaseBuilder(context, NoteDatabase::class.java, "notes")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}
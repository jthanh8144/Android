package com.example.note.data

import androidx.room.*
import com.example.note.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao {
    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes where id = :id")
    suspend fun getNoteById(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    @Query("DELETE from notes")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(note: Note)
}
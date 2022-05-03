package com.example.note.data

import com.example.note.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(val noteDatabaseDao: NoteDatabaseDao) {
    fun addNote(note: Note) = noteDatabaseDao.insert(note)

    fun updateNote(note: Note) = noteDatabaseDao.update(note)

    fun deleteNote(note: Note) = noteDatabaseDao.deleteNote(note)

    fun deleteALlNote() = noteDatabaseDao.deleteAll()

    fun getAllNotes(): Flow<List<Note>> = noteDatabaseDao.getNotes().flowOn(Dispatchers.IO)
}
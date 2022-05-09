package com.example.note.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.data.NoteRepository
import com.example.note.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val respository: NoteRepository) : ViewModel() {
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            respository.getAllNotes().distinctUntilChanged().collect {
                listOfNote ->
//                    Log.d("DEBUG0", "Here: ")
                    if (listOfNote.isNullOrEmpty()) {
                        Log.d("DEBUG0", "Empty: ")
                    } else {
//                        Log.d("DEBUG0", "No empty: ")
                        _noteList.value = listOfNote
                    }
            }
        }
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        respository.addNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        respository.updateNote(note)
    }

    fun removeNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        respository.deleteNote(note)
    }
}
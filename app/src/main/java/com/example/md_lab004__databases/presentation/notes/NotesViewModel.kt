package com.example.md_lab004__databases.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.md_lab004__databases.domain.model.Note
import com.example.md_lab004__databases.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notes = mutableStateOf<List<Note>>(emptyList())
    val notes: State<List<Note>> = _notes

    private var getNotesJob: Job? = null

    init {
        getNotes()
    }

    fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = repository.getNotes()
            .onEach {
                _notes.value = it
            }
            .launchIn(viewModelScope)
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            if (!note.title.isBlank() && !note.content.isBlank()) {
                repository.insertNote(note)
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}
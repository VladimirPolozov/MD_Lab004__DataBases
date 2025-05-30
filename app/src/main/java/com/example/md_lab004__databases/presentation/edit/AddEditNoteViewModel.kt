package com.example.md_lab004__databases.presentation.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.md_lab004__databases.domain.model.Note
import com.example.md_lab004__databases.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> = _noteTitle

    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> = _noteContent

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    repository.getNoteById(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = note.title
                        _noteContent.value = note.content
                    }
                }
            }
        }
    }

    fun enteredTitle(value: String) {
        _noteTitle.value = value
    }

    fun enteredContent(value: String) {
        _noteContent.value = value
    }

    fun saveNote() {
        viewModelScope.launch {
            try {
                repository.insertNote(
                    Note(
                        id = currentNoteId,
                        title = noteTitle.value,
                        content = noteContent.value
                    )
                )
            } catch (_: Exception) {

            }
        }
    }
}

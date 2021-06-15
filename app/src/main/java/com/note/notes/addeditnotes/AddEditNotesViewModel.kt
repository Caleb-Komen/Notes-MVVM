package com.note.notes.addeditnotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.note.notes.Event
import com.note.notes.R
import com.note.notes.data.Note
import com.note.notes.data.NotesRepository
import com.note.notes.data.Result
import kotlinx.coroutines.launch

class AddEditNotesViewModel(
    private val repository: NotesRepository
): ViewModel() {
    val noteTitle = MutableLiveData<String>()

    val noteBody = MutableLiveData<String>()

    var isNewNote: Boolean = false

    var noteId: Long = -1L

    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> get() = _snackbarMessage

    fun open(noteId: Long){
        if (noteId == -1L){
            isNewNote = true
            return
        }
        // There is an existing note. Display the note.....
        isNewNote = false
        this.noteId = noteId

        viewModelScope.launch {
            repository.getNote(noteId).let { result ->
                showNote(result)
            }
        }
    }

    private fun showNote(result: Result<Note>) {
        if (result is Result.Success){
            noteTitle.value = result.data.noteTitle
            noteBody.value = result.data.noteBody
        } else{
            _snackbarMessage.value = Event(R.string.load_note_error)
        }
    }

    fun save(){
        val title = noteTitle.value
        val body = noteBody.value
        if (title == null || body == null){
            _snackbarMessage.value = Event(R.string.snackbar_error_text)
            return
        }
        if (isNewNote){
            val note = Note(noteTitle = title, noteBody = body)
            createNote(note)
        } else{
            val note = Note(noteId, title, body)
            updateNote(note)
        }
    }

    private fun createNote(note: Note) = viewModelScope.launch {
        repository.saveNote(note)
        _snackbarMessage.value = Event(R.string.snackbar_create_new_successful)
    }

    private fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
        _snackbarMessage.value = Event(R.string.snackbar_update_successful_text)
    }
}
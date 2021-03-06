package com.note.notes.ui.notesdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.note.notes.Event
import com.note.notes.R
import com.note.notes.Util.DELETE_RESULT_OK
import com.note.notes.data.Note
import com.note.notes.data.NotesRepository
import com.note.notes.data.Result
import kotlinx.coroutines.launch
import timber.log.Timber

class NotesDetailsViewModel(
    private val repository: NotesRepository
): ViewModel(){
    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note?> get() = _note

    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> get() = _snackbarMessage

    private val _editNoteEvent = MutableLiveData<Event<Long>>()
    val editNoteEvent: LiveData<Event<Long>> get() = _editNoteEvent

    private val _deleteNoteEvent = MutableLiveData<Event<Int>>()
    val deleteNoteEvent: LiveData<Event<Int>> get() = _deleteNoteEvent

    fun getNote(noteId: Long) = viewModelScope.launch {
        repository.getNote(noteId).let { result ->
            _note.value = if (result is Result.Success){
                result.data
            } else{
                _snackbarMessage.value = Event(R.string.load_note_error)
                Timber.w("Unable to get note")
                null
            }
        }
    }

    fun editNote(noteId: Long){
        _editNoteEvent.value = Event(noteId)
    }

    fun deleteNote(noteId: Long) = viewModelScope.launch{
        repository.deleteNoteById(noteId)
        _deleteNoteEvent.value = Event(DELETE_RESULT_OK)
    }

}
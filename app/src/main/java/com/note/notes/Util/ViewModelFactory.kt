package com.note.notes.Util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.note.notes.addeditnotes.AddEditNotesViewModel
import com.note.notes.data.NotesRepository
import com.note.notes.notes.NotesViewModel
import com.note.notes.notesdetails.NotesDetailsViewModel

class ViewModelFactory(
    private val repository: NotesRepository,
    owner: SavedStateRegistryOwner,
    args: Bundle? = null
): AbstractSavedStateViewModelFactory(owner, args) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass){
        when{
            isAssignableFrom(NotesViewModel::class.java) ->
                NotesViewModel(repository, handle)
            isAssignableFrom(AddEditNotesViewModel::class.java) ->
                AddEditNotesViewModel(repository)
            isAssignableFrom(NotesDetailsViewModel::class.java) ->
                NotesDetailsViewModel(repository)
            else ->
                throw IllegalArgumentException("Unknown viewmodel class")
        }
    } as T
}
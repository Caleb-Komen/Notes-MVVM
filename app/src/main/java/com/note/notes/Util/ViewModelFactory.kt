package com.note.notes.Util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.note.notes.data.NotesRepository
import com.note.notes.notes.NotesViewModel

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
                return NotesViewModel(repository, handle) as T
            else ->
                throw IllegalArgumentException("Unknown viewmodel class")
        }
    } as T
}
package com.note.notes.Util

import androidx.fragment.app.Fragment
import com.note.notes.NotesApplication

fun Fragment.getViewModelFactory(): ViewModelFactory{
    val repository = (requireContext().applicationContext as NotesApplication).notesRepository
    return ViewModelFactory(repository, this)
}
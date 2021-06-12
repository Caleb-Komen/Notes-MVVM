package com.note.notes.Util

import androidx.fragment.app.Fragment
import com.note.notes.NotesApplication

fun Fragment.getViewModelFactory(): ViewModelFactory{
    val repository = (context as NotesApplication).notesRepository
    return ViewModelFactory(repository, this)
}
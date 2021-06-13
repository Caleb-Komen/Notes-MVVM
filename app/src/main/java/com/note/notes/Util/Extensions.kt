package com.note.notes.Util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.note.notes.Event
import com.note.notes.EventObserver
import com.note.notes.NotesApplication

fun Fragment.getViewModelFactory(): ViewModelFactory{
    val repository = (requireContext().applicationContext as NotesApplication).notesRepository
    return ViewModelFactory(repository, this)
}

fun View.setupSnackbar(lifecycleOwner: LifecycleOwner, message: LiveData<Event<Int>>){
    message.observe(lifecycleOwner, EventObserver{
        displaySnackBar(it)
    })
}

fun View.displaySnackBar(message: Int) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

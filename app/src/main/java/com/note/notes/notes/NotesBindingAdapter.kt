package com.note.notes.notes

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.note.notes.data.Note

@BindingAdapter("src")
fun setImage(view: ImageView, image: Int){
    view.setImageResource(image)
}

@BindingAdapter("items")
fun setNotes(view: RecyclerView, notes: List<Note>?){
    notes?.let {
        (view.adapter as NotesAdapter).submitList(notes)
    }
}
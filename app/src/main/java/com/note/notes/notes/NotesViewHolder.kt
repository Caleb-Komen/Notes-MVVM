package com.note.notes.notes

import androidx.recyclerview.widget.RecyclerView
import com.note.notes.R
import com.note.notes.data.Note
import com.note.notes.databinding.NoteItemBinding

class NotesViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note){
        binding.note = note
        binding.executePendingBindings()
    }
}
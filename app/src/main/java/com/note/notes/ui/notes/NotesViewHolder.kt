package com.note.notes.ui.notes

import androidx.recyclerview.widget.RecyclerView
import com.note.notes.data.Note
import com.note.notes.databinding.NoteItemBinding

class NotesViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note, notesViewModel: NotesViewModel){
        binding.note = note
        binding.viewmodel = notesViewModel
        binding.executePendingBindings()
    }
}
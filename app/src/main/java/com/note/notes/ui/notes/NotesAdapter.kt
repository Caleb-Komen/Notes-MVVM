package com.note.notes.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.note.notes.data.Note
import com.note.notes.databinding.NoteItemBinding

class NotesAdapter(
    private val viewModel: NotesViewModel
) : ListAdapter<Note, NotesViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note, viewModel)
    }
}

private val DIFF_UTIL = object : DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note) =
        oldItem.noteId == newItem.noteId

    override fun areContentsTheSame(oldItem: Note, newItem: Note) =
        oldItem == newItem
}

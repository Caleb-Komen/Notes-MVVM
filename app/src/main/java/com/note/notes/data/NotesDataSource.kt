package com.note.notes.data

import androidx.lifecycle.LiveData

interface NotesDataSource {
    fun observeNotes(): LiveData<Result<List<Note>>>

    suspend fun getNote(noteId: Long): Result<Note>

    suspend fun saveNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun bookmarkNote(noteId: Long)

    suspend fun removeBookmark(noteId: Long)

    suspend fun deleteAllNotes()

    suspend fun deleteNoteById(noteId: Long)
}
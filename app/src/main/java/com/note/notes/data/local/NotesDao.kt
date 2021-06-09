package com.note.notes.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.note.notes.data.Note

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun observeNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNote(noteId: Long)

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("UPDATE notes SET bookmarked = :bookmarked WHERE id = :noteId")
    suspend fun updateBookmarked(noteId: Long, bookmarked: Boolean)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Long)
}
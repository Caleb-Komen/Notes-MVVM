package com.note.notes.data

import androidx.lifecycle.LiveData
import com.note.notes.data.local.NotesLocalDataSource
import kotlinx.coroutines.coroutineScope

class DefaultNotesRepository(
    private val notesLocalDataSource: NotesLocalDataSource
): NotesRepository {
    override fun observeNotes(): LiveData<Result<List<Note>>> {
        return notesLocalDataSource.observeNotes()
    }

    override suspend fun getNote(noteId: Long): Result<Note> {
        return notesLocalDataSource.getNote(noteId)
    }

    override suspend fun saveNote(note: Note) {
        coroutineScope {
            notesLocalDataSource.saveNote(note)
        }
    }

    override suspend fun updateNote(note: Note) {
        coroutineScope {
            notesLocalDataSource.updateNote(note)
        }
    }

    override suspend fun bookmarkNote(noteId: Long) {
        coroutineScope {
            notesLocalDataSource.bookmarkNote(noteId)
        }
    }

    override suspend fun removeBookmark(noteId: Long) {
        coroutineScope {
            notesLocalDataSource.removeBookmark(noteId)
        }
    }

    override suspend fun deleteAllNotes() {
        coroutineScope {
            notesLocalDataSource.deleteAllNotes()
        }
    }

    override suspend fun deleteNoteById(noteId: Long) {
        coroutineScope {
            notesLocalDataSource.deleteNoteById(noteId)
        }
    }
}
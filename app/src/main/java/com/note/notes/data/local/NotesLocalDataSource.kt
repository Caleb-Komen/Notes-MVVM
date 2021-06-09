package com.note.notes.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.note.notes.data.Note
import com.note.notes.data.NotesDataSource
import com.note.notes.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesLocalDataSource(
    private val notesDao: NotesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): NotesDataSource {
    override fun observeNotes(): LiveData<Result<List<Note>>> {
        return notesDao.observeNotes().map {
            Result.Success(it)
        }
    }

    override suspend fun getNote(noteId: Long): Result<Note> = withContext(ioDispatcher) {
        return@withContext try {
            val note = notesDao.getNote(noteId)
            if (note != null){
                Result.Success(note)
            } else{
                Result.Error(Exception("Note not found"))
            }
        } catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun saveNote(note: Note) = withContext(ioDispatcher){
        notesDao.insertNote(note)
    }

    override suspend fun updateNote(note: Note) = withContext(ioDispatcher) {
        notesDao.updateNote(note)
    }

    override suspend fun bookmarkNote(noteId: Long) = withContext(ioDispatcher){
        notesDao.updateBookmarked(noteId, true)
    }

    override suspend fun removeBookmark(noteId: Long) = withContext(ioDispatcher) {
        notesDao.updateBookmarked(noteId, false)
    }

    override suspend fun deleteAllNotes() = withContext(ioDispatcher){
        notesDao.deleteAllNotes()
    }

    override suspend fun deleteNoteById(noteId: Long) = withContext(ioDispatcher){
        notesDao.deleteNoteById(noteId)
    }
}
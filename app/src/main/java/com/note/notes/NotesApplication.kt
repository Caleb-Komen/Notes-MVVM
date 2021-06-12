package com.note.notes

import android.app.Application
import android.content.Context
import com.note.notes.data.DefaultNotesRepository
import com.note.notes.data.NotesRepository
import com.note.notes.data.local.NotesDatabase
import com.note.notes.data.local.NotesLocalDataSource

class NotesApplication: Application() {
    lateinit var notesRepository: NotesRepository
    override fun onCreate() {
        super.onCreate()
        val noteDao = NotesDatabase.getInstance(this).notesDao
        val localDataSource = NotesLocalDataSource(noteDao)
        notesRepository = DefaultNotesRepository(localDataSource)
    }
}
package com.note.notes

import android.app.Application
import android.content.Context
import com.note.notes.data.DefaultNotesRepository
import com.note.notes.data.NotesRepository
import com.note.notes.data.local.NotesDatabase
import com.note.notes.data.local.NotesLocalDataSource
import timber.log.Timber

class NotesApplication: Application() {
    lateinit var notesRepository: NotesRepository
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        val noteDao = NotesDatabase.getInstance(this).notesDao
        val localDataSource = NotesLocalDataSource(noteDao)
        notesRepository = DefaultNotesRepository(localDataSource)
    }
}
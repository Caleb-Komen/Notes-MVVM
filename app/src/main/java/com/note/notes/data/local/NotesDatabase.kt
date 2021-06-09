package com.note.notes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.note.notes.data.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {
    abstract val notesDao: NotesDao

    companion object{
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(context: Context): NotesDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NotesDatabase::class.java,
                "notes_database"
            ).build()
        }
    }
}
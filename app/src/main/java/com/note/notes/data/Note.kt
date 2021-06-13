package com.note.notes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true)var noteId: Long = 0L,
    @ColumnInfo(name = "title") var noteTitle: String = "",
    @ColumnInfo(name = "body") var noteBody: String = "",
    @ColumnInfo(name = "bookmarked")var isBookmarked: Boolean = false
){
    val isMarked: Boolean get() = isBookmarked
}

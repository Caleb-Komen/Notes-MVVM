package com.note.notes.data

import kotlin.Exception

sealed class Result<out R>{
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
    object Loading : Result<Nothing>()
}

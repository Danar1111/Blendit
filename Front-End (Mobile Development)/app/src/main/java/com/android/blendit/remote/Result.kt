package com.android.blendit.remote

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val data: String) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}
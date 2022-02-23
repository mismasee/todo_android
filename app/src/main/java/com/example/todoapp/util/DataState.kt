package com.example.todoapp.util

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    class Success<T>(val data: T?) : DataState<T>()
    class Failure(val error: Throwable) : DataState<Nothing>()
}
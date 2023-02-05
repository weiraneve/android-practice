package com.thoughtworks.android.common

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T : Any>(val data: T?) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}
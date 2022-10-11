package com.thoughtworks.android.common

sealed class MyResult<out T> {
    object Loading : MyResult<Nothing>()
    data class Success<out T>(val data: T) : MyResult<T>()
    data class Error(val exception: Throwable) : MyResult<Nothing>()
}
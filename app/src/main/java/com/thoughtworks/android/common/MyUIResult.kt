package com.thoughtworks.android.common

sealed class MyUIResult<out T> {
    object Loading : MyUIResult<Nothing>()
    data class Success<out T>(val data: T) : MyUIResult<T>()
    data class Error(val exception: Throwable) : MyUIResult<Nothing>()
}
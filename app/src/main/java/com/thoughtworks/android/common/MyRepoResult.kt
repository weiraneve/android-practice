package com.thoughtworks.android.common

sealed class MyRepoResult<out T> {
    data class Success<out T>(val data: T) : MyRepoResult<T>()
    data class Error(val exception: Throwable) : MyRepoResult<Nothing>()
}
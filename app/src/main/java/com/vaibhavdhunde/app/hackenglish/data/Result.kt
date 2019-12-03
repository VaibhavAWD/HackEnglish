package com.vaibhavdhunde.app.hackenglish.data

import com.vaibhavdhunde.app.hackenglish.data.Result.Success

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()

    data class Error(val exception: String) : Result<Nothing>()

    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

@Suppress("unused")
val Result<*>.succeeded
    get() = this is Success && data != null
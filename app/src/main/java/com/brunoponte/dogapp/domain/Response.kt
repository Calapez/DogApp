package com.brunoponte.dogapp.domain

sealed class Response<out T> {
    data class Error(val exception: Exception) : Response<Nothing>()
    data class Success<T>(val data: T) : Response<T>()
}
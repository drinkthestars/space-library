package com.goofy.goober.api.util

sealed class Result<out T> {
    data class Success<T>(val data: T): Result<T>()
    class Fail<Any>: Result<Any>()
}

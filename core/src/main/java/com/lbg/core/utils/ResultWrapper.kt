package com.lbg.core.utils

/**
 * Sealed class representing the result of an operation.
 */
sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val exception: DomainException) : ResultWrapper<Nothing>()
}
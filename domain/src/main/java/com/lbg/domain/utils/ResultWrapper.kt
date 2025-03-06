package com.lbg.domain.utils

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val exception: DomainException) : ResultWrapper<Nothing>()
}
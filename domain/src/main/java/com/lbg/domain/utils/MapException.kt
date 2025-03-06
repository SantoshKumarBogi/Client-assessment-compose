package com.lbg.domain.utils

import retrofit2.HttpException

fun mapExceptionToDomainException(exception: Exception): DomainException {
    return when (exception) {
        is java.net.UnknownHostException -> DomainException.NetworkError
        is HttpException -> DomainException.ServerError
        else -> DomainException.UnknownError
    }
}
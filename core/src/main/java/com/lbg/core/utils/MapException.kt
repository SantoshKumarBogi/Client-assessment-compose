package com.lbg.core.utils

import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * Maps an exception to a domain exception.
 */
fun mapExceptionToDomainException(exception: Exception): DomainException {
    return when (exception) {
        is UnknownHostException -> DomainException.NetworkError
        is HttpException -> DomainException.ServerError
        else -> DomainException.UnknownError
    }
}
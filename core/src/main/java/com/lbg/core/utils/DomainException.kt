package com.lbg.core.utils

import com.lbg.core.ApiConstants

/**
 * Sealed class representing domain exceptions.
 */
sealed class DomainException(message: String) : Exception(message) {
    object NetworkError : DomainException(ApiConstants.NETWORK_ERROR) {
        private fun readResolve(): Any = NetworkError
    }

    object ServerError : DomainException(ApiConstants.SERVER_ERROR) {
        private fun readResolve(): Any = ServerError
    }

    object UnknownError : DomainException(ApiConstants.UNKNOWN_ERROR) {
        private fun readResolve(): Any = UnknownError
    }
}
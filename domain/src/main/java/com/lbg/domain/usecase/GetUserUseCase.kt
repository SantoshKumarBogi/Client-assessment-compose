package com.lbg.domain.usecase

import com.lbg.core.utils.ResultWrapper
import com.lbg.core.utils.mapExceptionToDomainException
import com.lbg.domain.model.User
import com.lbg.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Use case for fetching users. Handles business logic,
 * success and failure cases and returns the result.
 */
class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): ResultWrapper<List<User>> {
        return try {
            ResultWrapper.Success(userRepository.fetchUsers())
        } catch (exception: Exception) {
            ResultWrapper.Error(mapExceptionToDomainException(exception))
        }
    }
}
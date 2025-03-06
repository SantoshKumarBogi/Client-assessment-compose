package com.lbg.domain.usecase

import com.lbg.domain.model.User
import com.lbg.domain.repository.UserRepository
import com.lbg.domain.utils.ResultWrapper
import com.lbg.domain.utils.mapExceptionToDomainException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): ResultWrapper<List<User>> {
        return try {
            ResultWrapper.Success(userRepository.fetchUsers())
        } catch (exception: Exception) {
            ResultWrapper.Error(mapExceptionToDomainException(exception))
        }
    }
}
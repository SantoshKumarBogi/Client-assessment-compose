package com.lbg.domain.usecase

import com.lbg.domain.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.fetchUsers()
}
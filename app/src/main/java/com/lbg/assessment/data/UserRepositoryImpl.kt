package com.lbg.assessment.data

import com.lbg.assessment.domain.model.User
import com.lbg.assessment.domain.repository.UserRepository

class UserRepositoryImpl(private val userApiService: UserApiService) : UserRepository {
    override suspend fun fetchUsers(): List<User> {
        return userApiService.getUsers()
    }
}
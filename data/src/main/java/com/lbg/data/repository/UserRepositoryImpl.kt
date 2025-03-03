package com.lbg.data.repository

import com.lbg.data.service.UserApiService
import com.lbg.domain.model.User
import com.lbg.domain.repository.UserRepository

class UserRepositoryImpl(private val userApiService: UserApiService) : UserRepository {
    override suspend fun fetchUsers(): List<User> {
        return userApiService.getUsers()
    }
}
package com.lbg.data.repository

import com.lbg.data.service.UserApiService
import com.lbg.domain.model.User
import com.lbg.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Implementation of [UserRepository] that uses [UserApiService] to fetch users.
 */
class UserRepositoryImpl @Inject constructor(private val userApiService: UserApiService) :
    UserRepository {
    override suspend fun fetchUsers(): List<User> = userApiService.getUsers()
}
package com.lbg.data.repository

import com.lbg.data.service.UserApiService
import com.lbg.domain.model.User
import com.lbg.domain.repository.RemoteUserRepository
import javax.inject.Inject

/**
 * Implementation of [RemoteUserRepository] that uses [UserApiService] to fetch users.
 */
class RemoteUserRepositoryImpl @Inject constructor(private val apiService: UserApiService) :
    RemoteUserRepository {
    override suspend fun fetchUsers(): List<User> {
        return apiService.getUsers()
    }
}
package com.lbg.data.repository

import com.lbg.domain.model.User
import com.lbg.domain.repository.AssetUserRepository
import com.lbg.domain.repository.RemoteUserRepository
import com.lbg.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Implementation of [UserRepository] that uses [AssetUserRepository or RemoteUserRepository] to fetch users.
 */
class UserRepositoryImpl @Inject constructor(
    private val assetUserRepository: AssetUserRepository,
    private val remoteUserRepository: RemoteUserRepository,
) :
    UserRepository {
    override suspend fun fetchUsers(useLocal: Boolean): List<User> = if (useLocal)
        assetUserRepository.getUsers() else remoteUserRepository.fetchUsers()
}
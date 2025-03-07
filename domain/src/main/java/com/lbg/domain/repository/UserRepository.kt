package com.lbg.domain.repository

import com.lbg.domain.model.User

/**
 * Interface for the user repository.
 */
interface UserRepository {
    suspend fun fetchUsers(): List<User>
}
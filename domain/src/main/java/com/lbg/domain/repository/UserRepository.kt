package com.lbg.domain.repository

import com.lbg.domain.model.User

interface UserRepository {
    suspend fun fetchUsers(): List<User>
}
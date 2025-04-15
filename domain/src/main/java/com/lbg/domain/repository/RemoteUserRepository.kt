package com.lbg.domain.repository

import com.lbg.domain.model.User

interface RemoteUserRepository {
    suspend fun fetchUsers(): List<User>
}
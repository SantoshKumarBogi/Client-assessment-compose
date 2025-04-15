package com.lbg.domain.repository

import com.lbg.domain.model.User

interface AssetUserRepository {
    suspend fun getUsers(): List<User>
}
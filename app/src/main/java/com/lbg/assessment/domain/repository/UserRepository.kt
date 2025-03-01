package com.lbg.assessment.domain.repository

import com.lbg.assessment.domain.model.User

interface UserRepository {
    suspend fun fetchUsers(): List<User>
}
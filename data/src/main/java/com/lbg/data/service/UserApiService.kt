package com.lbg.data.service

import com.lbg.core.ApiConstants
import com.lbg.domain.model.User
import retrofit2.http.GET

interface UserApiService {
    @GET(ApiConstants.GET_ALL_USERS_EP)
    suspend fun getUsers(): List<User>
}
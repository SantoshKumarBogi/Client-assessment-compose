package com.lbg.data.service

import com.lbg.data.ApiConstants
import retrofit2.http.GET
import com.lbg.domain.model.User

interface UserApiService {
    @GET(ApiConstants.GET_ALL_USERS_EP)
    suspend fun getUsers(): List<User>
}
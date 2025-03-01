package com.lbg.assessment.data

import retrofit2.http.GET

interface UserApiService {
    @GET(ApiConstants.GET_ALL_USERS_EP)
    suspend fun getUsers()
}
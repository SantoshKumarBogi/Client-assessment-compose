package com.lbg.data.repository

import com.lbg.data.service.UserApiService
import com.lbg.domain.model.User
import com.lbg.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userApiService: UserApiService) :
    UserRepository {
    override suspend fun fetchUsers(): List<User> = userApiService.getUsers()

    // To simulate server error
    // throw HttpException(Response.error<ResponseBody>(500 ,ResponseBody.create(MediaType.parse("plain/text"),"some content")))
}
package com.lbg.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lbg.domain.model.User
import com.lbg.domain.repository.AssetUserRepository

class AssetUserRepositoryImpl(private val context: Context) : AssetUserRepository {
    override suspend fun getUsers(): List<User> {
        val json = context.assets.open("users.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val itemType = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(json, itemType)
    }
}
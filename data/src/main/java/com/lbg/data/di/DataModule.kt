package com.lbg.data.di

import android.content.Context
import com.lbg.core.ApiConstants.BASE_URL
import com.lbg.data.repository.AssetUserRepositoryImpl
import com.lbg.data.repository.RemoteUserRepositoryImpl
import com.lbg.data.repository.UserRepositoryImpl
import com.lbg.data.service.UserApiService
import com.lbg.domain.repository.AssetUserRepository
import com.lbg.domain.repository.RemoteUserRepository
import com.lbg.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dependency injection module for the app.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideAssetUserRepository(@ApplicationContext context: Context): AssetUserRepository =
        AssetUserRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideRemoteUserRepository(userApiService: UserApiService): RemoteUserRepository =
        RemoteUserRepositoryImpl(userApiService)

    @Provides
    @Singleton
    fun providesUserRepository(
        assetUserRepository: AssetUserRepository,
        remoteUserRepository: RemoteUserRepository,
    ): UserRepository =
        UserRepositoryImpl(assetUserRepository, remoteUserRepository)
}
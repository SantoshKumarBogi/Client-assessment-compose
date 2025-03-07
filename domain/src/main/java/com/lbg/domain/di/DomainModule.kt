package com.lbg.domain.di

import com.lbg.domain.repository.UserRepository
import com.lbg.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency injection module for the app.
 */
@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun providesGetUserUseCase(userRepository: UserRepository): GetUserUseCase =
        GetUserUseCase(userRepository)
}
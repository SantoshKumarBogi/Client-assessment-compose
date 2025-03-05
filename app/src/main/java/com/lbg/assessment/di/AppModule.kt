package com.lbg.assessment.di

import com.lbg.assessment.presentation.viewmodel.UserViewModel
import com.lbg.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun providesUserViewModel(getUserUseCase: GetUserUseCase): UserViewModel =
        UserViewModel(getUserUseCase)

}
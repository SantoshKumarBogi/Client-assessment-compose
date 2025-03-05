package com.lbg.assessment.presentation.state

import com.lbg.domain.model.User

sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    data class Success(val users: List<User>) : UserState()
    data class Error(val error: String?) : UserState()
}
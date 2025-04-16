package com.lbg.assessment.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.core.utils.ResultWrapper
import com.lbg.domain.model.User
import com.lbg.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * ViewModel class for the User Screen. Communicates to use case to fetch users.
 */
@HiltViewModel
class UserViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _error

    init {
        fetchUsers()
    }

    /**
     * Fetch users from the use case and update the UI state accordingly.
     */
    fun fetchUsers(useLocal: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = getUserUseCase(useLocal)) {
                is ResultWrapper.Success -> _users.value = result.value
                is ResultWrapper.Error -> _error.value = result.exception.message
            }
            _isLoading.value = false
        }
    }

    // Encode User object to json
    suspend fun encodeUserObjectToJson(user: User): String = withContext(Dispatchers.IO) {
        Uri.encode(Json.encodeToString(user))
    }

    // Decode User object from json
    suspend fun decodeFromJson(userJson: String): User = withContext(Dispatchers.IO) {
        Json.decodeFromString(userJson)
    }
}
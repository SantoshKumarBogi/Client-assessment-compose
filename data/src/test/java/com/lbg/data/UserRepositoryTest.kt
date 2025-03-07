package com.lbg.data

import com.lbg.data.repository.UserRepositoryImpl
import com.lbg.data.service.UserApiService
import com.lbg.domain.model.User
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 * Unit tests for [UserRepositoryImpl].
 */
class UserRepositoryTest {

    // region constants

    // end region constants

    // region helper fields
    private lateinit var userApiService: UserApiService

    // endregion helper fields

    private lateinit var SUT: UserRepositoryImpl

    @Before
    fun setUp() {
        userApiService = Mockito.mock(UserApiService::class.java)
        SUT = UserRepositoryImpl(userApiService)
    }

    @Test
    fun fetchUsers_success_returnsListOfUsers_checkValidDataTypes() = runBlocking {
        // Arrange
        val mockUsers = listOf(User(1, "Santosh", "santosh@gmail.com", "1234567890"))
        // Act
        Mockito.`when`(userApiService.getUsers()).thenReturn(mockUsers)
        val result = SUT.fetchUsers()
        // Assert: check data types for all users
        assertEquals(mockUsers, result)
        result.forEach { user ->
            assertEquals(user.id::class.java, Int::class.java)
            assertEquals(user.name::class.java, String::class.java)
            assertEquals(user.email::class.java, String::class.java)
            assertEquals(user.phone::class.java, String::class.java)
        }
    }

    @Test
    fun fetchUsers_failure_throwsException() = runBlocking {
        // Arrange
        val exception = RuntimeException("Network error")
        // Act
        Mockito.`when`(userApiService.getUsers()).thenThrow(exception)
        // Assert
        try {
            SUT.fetchUsers()
            fail("Expected an exception but none was thrown")
        } catch (e: Exception) {
            assertEquals(exception.message, e.message)
        }
    }

    @Test
    fun fetchUsers_emptyResponse_returnsEmptyList() = runBlocking {
        // Arrange
        Mockito.`when`(userApiService.getUsers()).thenReturn(emptyList())
        // Act
        val result = SUT.fetchUsers()
        // Assert
        assertEquals(emptyList<User>(), result)
    }

// region helper methods

// end region helper methods

// region helper classes

// endregion helper classes
}
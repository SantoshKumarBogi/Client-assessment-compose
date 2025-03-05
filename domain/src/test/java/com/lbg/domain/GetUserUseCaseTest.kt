package com.lbg.domain

import com.lbg.domain.model.User
import com.lbg.domain.repository.UserRepository
import com.lbg.domain.usecase.GetUserUseCase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetUserUseCaseTest {
    // region constants

    // end region constants

    // region helper fields
    private lateinit var mockUserRepository: UserRepository

    // endregion helper fields

    private lateinit var SUT: GetUserUseCase

    @Before
    fun setUp() {
        mockUserRepository = Mockito.mock(UserRepository::class.java)
        SUT = GetUserUseCase(mockUserRepository)
    }

    @Test
    fun invoke_success_returnsListOfUsers_checkValidDataTypes() = runBlocking {
        // Arrange
        val mockUsers = listOf(
            User(1, "Santosh", "santosh@gmail.com", "1234567890"),
            User(2, "Varghese", "varghese@gmail.com", "9876543210")
        )
        Mockito.`when`(mockUserRepository.fetchUsers()).thenReturn(mockUsers)

        // Act
        val result = SUT()

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
    fun invoke_failure_throwsException() = runBlocking {
        // Arrange
        val exception = RuntimeException("Network error")
        // Act
        Mockito.`when`(mockUserRepository.fetchUsers()).thenThrow(exception)
        // Assert
        try {
            SUT()
            fail("Expected an exception but none was thrown")
        } catch (e: Exception) {
            assertEquals(exception.message, e.message)
        }
    }

    @Test
    fun invoke_emptyResponse_returnsEmptyList() = runBlocking {
        // Arrange
        Mockito.`when`(mockUserRepository.fetchUsers()).thenReturn(emptyList())
        // Act
        val result = SUT()
        // Assert
        assertEquals(emptyList<User>(), result)
    }

// region helper methods

// end region helper methods

// region helper classes

// endregion helper classes
}
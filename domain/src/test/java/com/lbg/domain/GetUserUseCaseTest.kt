package com.lbg.domain

import com.lbg.domain.model.User
import com.lbg.domain.repository.UserRepository
import com.lbg.domain.usecase.GetUserUseCase
import com.lbg.core.utils.DomainException
import com.lbg.core.utils.ResultWrapper
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response

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
    fun invoke_success_returnsListOfUsers_checkValidDataTypes_when_useLocal_is_true() = runTest {
        // Arrange
        val mockUsers = listOf(
            User(1, "Santosh", "santosh@gmail.com", "1234567890"),
            User(2, "Varghese", "varghese@gmail.com", "9876543210")
        )
        Mockito.`when`(mockUserRepository.fetchUsers(true)).thenReturn(mockUsers)

        // Act
        val result = SUT(true)

        // Assert: check data types for all users
        assertEquals(ResultWrapper.Success(mockUsers), result)
        (result as ResultWrapper.Success).value.forEach { user ->
            assertEquals(user.id::class.java, Int::class.java)
            assertEquals(user.name::class.java, String::class.java)
            assertEquals(user.email::class.java, String::class.java)
            assertEquals(user.phone::class.java, String::class.java)
        }
    }

    @Test
    fun invoke_success_returnsListOfUsers_checkValidDataTypes_when_useLocal_is_false() = runTest {
        // Arrange
        val mockUsers = listOf(
            User(1, "Santosh", "santosh@gmail.com", "1234567890"),
            User(2, "Varghese", "varghese@gmail.com", "9876543210")
        )
        Mockito.`when`(mockUserRepository.fetchUsers(false)).thenReturn(mockUsers)

        // Act
        val result = SUT(false)

        // Assert: check data types for all users
        assertEquals(ResultWrapper.Success(mockUsers), result)
        (result as ResultWrapper.Success).value.forEach { user ->
            assertEquals(user.id::class.java, Int::class.java)
            assertEquals(user.name::class.java, String::class.java)
            assertEquals(user.email::class.java, String::class.java)
            assertEquals(user.phone::class.java, String::class.java)
        }
    }

    @Test
    fun invoke_failure_throwsException_when_useLocal_is_true() = runBlocking {
        // Arrange
        val exception = HttpException(
            Response.error<ResponseBody>(
                500,
                ResponseBody.create(MediaType.parse("plain/text"), "some content")
            )
        )
        Mockito.`when`(mockUserRepository.fetchUsers(true)).thenThrow(exception)

        // Act
        val result = SUT(true)

        // Assert
        assert(result is ResultWrapper.Error)
        assertEquals(DomainException.ServerError, (result as ResultWrapper.Error).exception)
    }

    @Test
    fun invoke_failure_throwsException_when_useLocal_is_false() = runBlocking {
        // Arrange
        val exception = HttpException(
            Response.error<ResponseBody>(
                500,
                ResponseBody.create(MediaType.parse("plain/text"), "some content")
            )
        )
        Mockito.`when`(mockUserRepository.fetchUsers(false)).thenThrow(exception)

        // Act
        val result = SUT(false)

        // Assert
        assert(result is ResultWrapper.Error)
        assertEquals(DomainException.ServerError, (result as ResultWrapper.Error).exception)
    }

    @Test
    fun invoke_emptyResponse_returnsEmptyList_when_useLocal_is_true() = runBlocking {
        // Arrange
        Mockito.`when`(mockUserRepository.fetchUsers(true)).thenReturn(emptyList())
        // Act
        val result = SUT(true)
        // Assert
        assertEquals(emptyList<User>(), (result as ResultWrapper.Success).value)
    }

    @Test
    fun invoke_emptyResponse_returnsEmptyList_when_useLocal_is_false() = runBlocking {
        // Arrange
        Mockito.`when`(mockUserRepository.fetchUsers(false)).thenReturn(emptyList())
        // Act
        val result = SUT(false)
        // Assert
        assertEquals(emptyList<User>(), (result as ResultWrapper.Success).value)
    }

// region helper methods

// end region helper methods

// region helper classes

// endregion helper classes
}
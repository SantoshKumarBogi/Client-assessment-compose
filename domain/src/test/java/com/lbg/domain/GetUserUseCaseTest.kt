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
    fun invoke_success_returnsListOfUsers_checkValidDataTypes() = runTest {
        // Arrange
        val mockUsers = listOf(
            User(1, "Santosh", "santosh@gmail.com", "1234567890"),
            User(2, "Varghese", "varghese@gmail.com", "9876543210")
        )
        Mockito.`when`(mockUserRepository.fetchUsers()).thenReturn(mockUsers)

        // Act
        val result = SUT()

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
    fun invoke_failure_throwsException() = runBlocking {
        // Arrange
        val exception = HttpException(
            Response.error<ResponseBody>(
                500,
                ResponseBody.create(MediaType.parse("plain/text"), "some content")
            )
        )
        Mockito.`when`(mockUserRepository.fetchUsers()).thenThrow(exception)

        // Act
        val result = SUT()

        // Assert
        assert(result is ResultWrapper.Error)
        assertEquals(DomainException.ServerError, (result as ResultWrapper.Error).exception)
    }

    @Test
    fun invoke_emptyResponse_returnsEmptyList() = runBlocking {
        // Arrange
        Mockito.`when`(mockUserRepository.fetchUsers()).thenReturn(emptyList())
        // Act
        val result = SUT()
        // Assert
        assertEquals(emptyList<User>(), (result as ResultWrapper.Success).value)
    }

// region helper methods

// end region helper methods

// region helper classes

// endregion helper classes
}
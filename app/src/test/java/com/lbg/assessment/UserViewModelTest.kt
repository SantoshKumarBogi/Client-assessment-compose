package com.lbg.assessment

import app.cash.turbine.test
import com.lbg.assessment.presentation.viewmodel.UserViewModel
import com.lbg.domain.model.User
import com.lbg.domain.usecase.GetUserUseCase
import com.lbg.domain.utils.DomainException
import com.lbg.domain.utils.ResultWrapper
import com.lbg.util.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelTest {

    // region constants

    // end region constants

    // region helper fields
    private var getUserUseCase: GetUserUseCase = mockk()

    // endregion helper fields

    private lateinit var SUT: UserViewModel

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        coEvery { getUserUseCase.invoke() } returns ResultWrapper.Success(emptyList())
        SUT = UserViewModel(getUserUseCase)
    }

    @Test
    fun fetchUser_success_returnsListOfUsers() = runBlocking {
        // Arrange
        val mockUsers = listOf(
            User(1, "Santosh", "santosh@gmail.com", "1234567890"),
            User(2, "Varghese", "varghese@gmail.com", "9876543210")
        )
        coEvery { getUserUseCase() } returns ResultWrapper.Success(mockUsers)

        // Act
        SUT = UserViewModel(getUserUseCase)

        // Assert
        SUT.users.test {
            assertEquals(mockUsers, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { getUserUseCase() }

    }

    @Test
    fun fetchUser_failure_returnsError() = runBlocking {
        // Arrange
        coEvery { getUserUseCase() } returns ResultWrapper.Error(DomainException.ServerError)

        // Act
        SUT = UserViewModel(getUserUseCase)

        // Assert
        SUT.errorMessage.test {
            assertEquals(DomainException.ServerError.message, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun fetchUser_loading_returnsLoading() = runTest {
        // Arrange
        val mockUsers = listOf(
            User(1, "Santosh", "santosh@gmail.com", "1234567890"),
            User(2, "Varghese", "varghese@gmail.com", "9876543210")
        )
        coEvery {
            getUserUseCase()
        } coAnswers {
            delay(100)
            ResultWrapper.Success(mockUsers)
        }
        // Act
        SUT = UserViewModel(getUserUseCase)

        // Assert
        SUT.isLoading.test {
            assertEquals(true, awaitItem())
            assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
package com.lbg.assessment

import app.cash.turbine.test
import com.lbg.assessment.presentation.viewmodel.UserViewModel
import com.lbg.domain.model.User
import com.lbg.domain.usecase.GetUserUseCase
import com.lbg.util.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
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
        coEvery { getUserUseCase.invoke() } returns emptyList()
        SUT = UserViewModel(getUserUseCase)
    }

    @Test
    fun fetchUser_success_returnsListOfUsers() = runBlocking {
        // Arrange
        val mockUsers = listOf(
            User(1, "Santosh", "santosh@gmail.com", "1234567890"),
            User(2, "Varghese", "varghese@gmail.com", "9876543210")
        )
        coEvery { getUserUseCase() } returns mockUsers

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
        coEvery { getUserUseCase() } throws RuntimeException("Network error")
        // Act
        SUT = UserViewModel(getUserUseCase)
        // Assert
        SUT.errorMessage.test {
            assertEquals("Network error", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun fetchUser_loading_returnsLoading() = runBlocking {
        // Arrange
        coEvery { getUserUseCase.invoke() } coAnswers {
            delay(100)
            listOf(
                User(1, "Santosh", "santosh@gmail.com", "1234567890"),
                User(2, "Varghese", "varghese@gmail.com", "9876543210")
            )
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
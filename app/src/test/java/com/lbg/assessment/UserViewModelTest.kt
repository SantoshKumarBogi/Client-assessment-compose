package com.lbg.assessment

import app.cash.turbine.test
import com.lbg.assessment.presentation.viewmodel.UserViewModel
import com.lbg.core.utils.DomainException
import com.lbg.core.utils.ResultWrapper
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
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit test for [UserViewModel]
 */
@ExperimentalCoroutinesApi
class UserViewModelTest {

    // region constants

    // end region constants

    private val mockUsers = listOf(
        User(
            1,
            "Leanne Graham",
            "Sincere@april.biz",
            "1-770-736-8031 x56442",
            "Bret",
            User.Address(
                "Kulas Light",
                "Apt. 556",
                "Gwenborough",
                "92998-3874",
                User.Location(
                    "-37.3159",
                    "81.1496"
                )
            ),
            "hildegard.org",
            User.Company(
                "Romaguera-Crona",
                "Multi-layered client-server neural-net",
                "harness real-time e-markets"
            )
        ),
        User(
            2,
            "Ervin Howell",
            "Shanna@melissa.tv",
            "010-692-6593 x09125",
            "Antonette",
            User.Address(
                "Victor Plains",
                "Suite 879",
                "Wisokyburgh",
                "90566-7771",
                User.Location(
                    "-43.9509",
                    "-34.4618"
                )
            ),
            "anastasia.net",
            User.Company(
                "Deckow-Crist",
                "Proactive didactic contingency",
                "synergize scalable supply-chains"
            )
        )
    )

    // region helper fields
    private var getUserUseCase: GetUserUseCase = mockk()

    // endregion helper fields

    private lateinit var SUT: UserViewModel

    // test rule
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        coEvery { getUserUseCase.invoke(false) } returns ResultWrapper.Success(emptyList())
        SUT = UserViewModel(getUserUseCase)
    }

    @Test
    fun fetchUser_success_returnsListOfUsers_when_useLocal_is_true() = runBlocking {
        // Arrange
        coEvery { getUserUseCase(true) } returns ResultWrapper.Success(mockUsers)

        // Act
        SUT = UserViewModel(getUserUseCase)
        SUT.fetchUsers(true)

        // Assert
        SUT.users.test {
            assertEquals(mockUsers, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { getUserUseCase(true) }

    }

    @Test
    fun fetchUser_success_returnsListOfUsers_when_useLocal_is_false() = runBlocking {
        coEvery { getUserUseCase.invoke(false) } returns ResultWrapper.Success(emptyList())
        // Arrange
        coEvery { getUserUseCase(false) } returns ResultWrapper.Success(mockUsers)

        // Act
        SUT = UserViewModel(getUserUseCase)
        SUT.fetchUsers(false)

        // Assert
        SUT.users.test {
            assertEquals(mockUsers, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { getUserUseCase(false) }

    }

    @Test
    fun fetchUser_failure_returnsError_when_useLocal_is_true() = runBlocking {
        // Arrange
        coEvery { getUserUseCase(true) } returns ResultWrapper.Error(DomainException.ServerError)

        // Act
        SUT = UserViewModel(getUserUseCase)
        SUT.fetchUsers(true)

        // Assert
        SUT.errorMessage.test {
            assertEquals(DomainException.ServerError.message, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun fetchUser_failure_returnsError_when_useLocal_is_false() = runBlocking {
        // Arrange
        coEvery { getUserUseCase(false) } returns ResultWrapper.Error(DomainException.ServerError)

        // Act
        SUT = UserViewModel(getUserUseCase)
        SUT.fetchUsers(false)

        // Assert
        SUT.errorMessage.test {
            assertEquals(DomainException.ServerError.message, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun fetchUser_loading_returnsLoading_when_useLocal_is_true() = runTest {
        coEvery { getUserUseCase(true) } returns ResultWrapper.Success(mockUsers)
        // Arrange
        coEvery {
            getUserUseCase(true)
        } coAnswers {
            delay(100)
            ResultWrapper.Success(mockUsers)
        }
        // Act
        SUT = UserViewModel(getUserUseCase)

        SUT.fetchUsers(true)

        // Assert
        SUT.isLoading.test {
            assertEquals(true, awaitItem())
            assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun fetchUser_loading_returnsLoading_when_useLocal_is_false() = runTest {
        // Arrange
        coEvery {
            getUserUseCase(false)
        } coAnswers {
            delay(100)
            ResultWrapper.Success(mockUsers)
        }
        // Act
        SUT = UserViewModel(getUserUseCase)
        SUT.fetchUsers(false)

        // Assert
        SUT.isLoading.test {
            assertEquals(true, awaitItem())
            assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
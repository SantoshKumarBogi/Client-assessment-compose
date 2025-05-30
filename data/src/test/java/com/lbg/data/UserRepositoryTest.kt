package com.lbg.data

import com.lbg.data.repository.UserRepositoryImpl
import com.lbg.domain.model.User
import com.lbg.domain.repository.AssetUserRepository
import com.lbg.domain.repository.RemoteUserRepository
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

    // endregion helper fields

    private lateinit var SUT: UserRepositoryImpl
    private lateinit var remoteUserRepository: RemoteUserRepository
    private lateinit var assetUserRepository: AssetUserRepository

    @Before
    fun setUp() {
        remoteUserRepository = Mockito.mock(RemoteUserRepository::class.java)
        assetUserRepository = Mockito.mock(AssetUserRepository::class.java)
        SUT = UserRepositoryImpl(assetUserRepository, remoteUserRepository)
    }

    @Test
    fun fetchUsers_should_return_success_returnsListOfUsers_checkValidDataTypes_from_assetUserRepository_when_useLocal_is_true() =
        runBlocking {
            // Arrange
            // Act
            Mockito.`when`(assetUserRepository.getUsers()).thenReturn(mockUsers)
            val result = SUT.fetchUsers(true)
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
    fun fetchUsers_should_return_success_returnsListOfUsers_checkValidDataTypes_from_remoteUserRepository_when_useLocal_is_false() =
        runBlocking {
            // Arrange
            // Act
            Mockito.`when`(remoteUserRepository.fetchUsers()).thenReturn(mockUsers)
            val result = SUT.fetchUsers(false)
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
    fun fetchUsers_should_return_failure_throwsException_from_assetUserRepository_when_useLocal_is_true() =
        runBlocking {
            // Arrange
            val exception = RuntimeException("Asset file not found error")
            // Act
            Mockito.`when`(assetUserRepository.getUsers()).thenThrow(exception)
            // Assert
            try {
                SUT.fetchUsers(true)
                fail("Expected an exception but none was thrown")
            } catch (e: Exception) {
                assertEquals(exception.message, e.message)
            }
        }

    @Test
    fun fetchUsers_should_return_failure_throwsException_from_remoteUserRepository_when_useLocal_is_false() =
        runBlocking {
            // Arrange
            val exception = RuntimeException("Network error")
            // Act
            Mockito.`when`(remoteUserRepository.fetchUsers()).thenThrow(exception)
            // Assert
            try {
                SUT.fetchUsers(false)
                fail("Expected an exception but none was thrown")
            } catch (e: Exception) {
                assertEquals(exception.message, e.message)
            }
        }

    @Test
    fun fetchUsers_should_return_emptyResponse_returnsEmptyList_from_assetUserRepository_when_useLocal_is_true() =
        runBlocking {
            // Arrange
            Mockito.`when`(assetUserRepository.getUsers()).thenReturn(emptyList())
            // Act
            val result = SUT.fetchUsers(true)
            // Assert
            assertEquals(emptyList<User>(), result)
        }

    @Test
    fun fetchUsers_should_return_emptyResponse_returnsEmptyList_from_remoteUserRepository_when_useLocal_is_false() =
        runBlocking {
            // Arrange
            Mockito.`when`(remoteUserRepository.fetchUsers()).thenReturn(emptyList())
            // Act
            val result = SUT.fetchUsers(false)
            // Assert
            assertEquals(emptyList<User>(), result)
        }

// region helper methods

// end region helper methods

// region helper classes

// endregion helper classes
}
package com.davidluna.jwtauth.data

import arrow.core.Either
import com.davidluna.jwtauth.data.sources.AuthDataSource
import com.davidluna.jwtauth.data.sources.HashDataSource
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.testshared.fakeAuthRequest
import com.davidluna.jwtauth.testshared.fakeSaltedHash
import com.davidluna.jwtauth.testshared.fakeUser
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    @Mock
    lateinit var auth: AuthDataSource

    @Mock
    lateinit var hash: HashDataSource

    private lateinit var repository: AuthRepository

    @Before
    fun setUp() {
        repository = AuthRepository(auth, hash)
    }

    @Test
    fun `given a valid auth request when login is called if successful then return user on right side of either`() =
        runTest {
            val expected = Either.Right(fakeUser)
            whenever(auth.userExists(fakeAuthRequest)).thenReturn(Either.Right(true))
            whenever(hash.createSaltedHash(fakeAuthRequest.password)).thenReturn(fakeSaltedHash)
            whenever(hash.validateHash(fakeAuthRequest.password, fakeSaltedHash)).thenReturn(true)
            whenever(auth.findUser(fakeAuthRequest)).thenReturn(expected)

            val actual = repository.loginUser(fakeAuthRequest)

            Truth.assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given a non existent auth request when login is called if user does not exist then return user not found on left side of either`() =
        runTest {
            val expected = Either.Left(AppError.UserNotFound(400))
            whenever(auth.userExists(fakeAuthRequest)).thenReturn(Either.Right(false))

            val actual = repository.loginUser(fakeAuthRequest)

            Truth.assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given an invalid auth request when login is called if password is invalid then return unauthorized on left side of either`() =
        runTest {
            val expected = Either.Left(AppError.UnAuthorized(400))
            whenever(auth.userExists(fakeAuthRequest)).thenReturn(Either.Right(true))
            whenever(hash.createSaltedHash(fakeAuthRequest.password)).thenReturn(fakeSaltedHash)
            whenever(hash.validateHash(fakeAuthRequest.password, fakeSaltedHash)).thenReturn(false)

            val actual = repository.loginUser(fakeAuthRequest)

            Truth.assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given a valid auth request when register is called if successful then return true on right side of either`() =
        runTest {
            val expected = Either.Right(true)
            whenever(auth.userExists(fakeAuthRequest)).thenReturn(Either.Right(false))
            whenever(hash.createSaltedHash(fakeAuthRequest.password)).thenReturn(fakeSaltedHash)
            whenever(auth.saveUser(fakeAuthRequest, fakeSaltedHash)).thenReturn(expected)

            val actual = repository.registerUser(fakeAuthRequest)

            Truth.assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given an existing auth request when register is called if user exists then return account exists on left side of either`() =
        runTest {
            val expected = Either.Left(AppError.AccountExists(400))
            whenever(auth.userExists(fakeAuthRequest)).thenReturn(Either.Right(true))

            val actual = repository.registerUser(fakeAuthRequest)

            Truth.assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given a valid auth request when findUser is called if successful then return user on right side of either`() =
        runTest {
            val expected = Either.Right(fakeUser)
            whenever(auth.findUser(fakeAuthRequest)).thenReturn(expected)

            val actual = repository.findUser(fakeAuthRequest)

            Truth.assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given a non existent auth request when findUser is called if user does not exist then return user not found on left side of either`() =
        runTest {
            val expected = Either.Left(AppError.UserNotFound(400))
            whenever(auth.findUser(fakeAuthRequest)).thenReturn(Either.Right(null))

            val actual = repository.findUser(fakeAuthRequest)

            Truth.assertThat(actual).isEqualTo(expected)
        }

}
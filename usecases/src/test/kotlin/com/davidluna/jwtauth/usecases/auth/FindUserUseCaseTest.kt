package com.davidluna.jwtauth.usecases.auth

import arrow.core.Either
import com.davidluna.jwtauth.data.AuthRepository
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.testshared.fakeAuthRequest
import com.davidluna.jwtauth.testshared.fakeUser
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class FindUserUseCaseTest {

    @Mock
    lateinit var repository: AuthRepository

    private val useCase by lazy { FindUserUseCase(repository) }

    @Test
    fun `given a valid auth request when invoke is called then return user on right side of either`() = runTest {
        val expected = Either.Right(fakeUser)
        whenever(repository.findUser(fakeAuthRequest)).thenReturn(expected)

        val actual = useCase(fakeAuthRequest)

        Truth.assertThat(actual).isEqualTo(expected)

    }

    @Test
    fun `given an invalid auth request when invoke is called then return error on left side of either`() = runTest {
        val expected = Either.Left(AppError.UserNotFound(400))
        whenever(repository.findUser(fakeAuthRequest)).thenReturn(expected)

        val actual = useCase(fakeAuthRequest)

        Truth.assertThat(actual).isEqualTo(expected)
    }

}
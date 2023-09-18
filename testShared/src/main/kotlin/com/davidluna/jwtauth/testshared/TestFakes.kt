package com.davidluna.jwtauth.testshared

import com.davidluna.jwtauth.domain.*

val fakeAuthRequest = AuthRequest("david", "1234")
val fakeUser = User(
    id = "luptatum",
    username = "Emerson Shelton",
    password = "ne",
    salt = "nulla",
    role = Role.ADMIN
)
val fakeStatus = StatusCode(value = 7092, description = "diam")

val fakeSaltedHash = SaltedHash(
    salt = "dolore",
    hash = "dolore"
)

fun <T> T.getFakeResponse(): Response<T> = Response(
    code = fakeStatus,
    message = "falli",
    token = "utroque",
    body = this

)
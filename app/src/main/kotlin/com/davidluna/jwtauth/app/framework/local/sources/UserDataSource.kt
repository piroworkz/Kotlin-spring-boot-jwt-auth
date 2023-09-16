package com.davidluna.jwtauth.app.framework.local.sources

import com.davidluna.jwtauth.app.framework.local.database.daos.UserDao
import com.davidluna.jwtauth.app.framework.local.database.entities.DBUser
import com.davidluna.jwtauth.app.r.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDataSource(
    @Qualifier(R.Strings.SCOPE_IO)
    private val scope: CoroutineScope,
    private val dao: UserDao,
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user: DBUser = username?.let(::getUser) ?: throw UsernameNotFoundException("$username not found")
        val authority = SimpleGrantedAuthority(user.role)
        return User(user.username, user.password, listOf(authority))
    }

    private fun getUser(username: String): DBUser? {
        var user: DBUser? = null
        scope.launch { user = dao.findByUsername(username) }
        return user
    }
}
package com.rys.springrysakovbohdan.service

import com.rys.springrysakovbohdan.repository.UserDao
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl(private val userRepository: UserDao) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        userRepository.findByLogin(username)?.let {
            return User(it.login, it.password, ArrayList())
        } ?: throw UsernameNotFoundException("User not found with username: $username")
    }
}


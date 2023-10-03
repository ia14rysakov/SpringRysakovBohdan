package com.rys.springrysakovbohdan.config.security

import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.repository.impl.data.UserRepository
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User =
            userRepository.findByLogin(username) ?: throw UsernameNotFoundException("User not found")
        return CustomUserDetails(user)
    }
}

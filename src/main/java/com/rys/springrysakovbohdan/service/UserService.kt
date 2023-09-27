package com.rys.springrysakovbohdan.service

import com.rys.springrysakovbohdan.model.User
import org.springframework.stereotype.Service

@Service
interface UserService {

    fun createUser(user: User): User

    fun getUserById(id: String): User?

    fun getAllUsers(): List<User>

    fun updateUserById(targetId: String, user: User, currentId: String): User

    fun deleteUserById(targetId: String, currentId: String): Boolean
}

package com.rys.springrysakovbohdan.repository

import com.rys.springrysakovbohdan.model.User

interface UserDAO {
    fun getUserById(id: String): User?

    fun createUser(user: User): User

    fun getAllUsers(): List<User>

    fun deleteUserById(id: String): Boolean
}

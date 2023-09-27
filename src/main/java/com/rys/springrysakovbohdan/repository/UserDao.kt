package com.rys.springrysakovbohdan.repository

import com.rys.springrysakovbohdan.model.User

interface UserDao {
    fun findById(id: String): User?

    fun create(user: User): User

    fun findAll(): List<User>

    fun deleteById(id: String): Boolean

    fun deleteAll() : Boolean

    fun findByEmail(email: String): User?

    fun findByLogin(login: String): User?


}

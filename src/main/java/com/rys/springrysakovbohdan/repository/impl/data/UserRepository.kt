package com.rys.springrysakovbohdan.repository.impl.data

import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.repository.UserDao
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val userRepository: UserMongoRepository) : UserDao {
    override fun findById(id: String): User? {
        return userRepository.findUserById(id)
    }

    override fun create(user: User): User {
        return userRepository.save(user)
    }

    override fun findAll(): List<User> {
        return userRepository.findAll()
    }

    override fun deleteById(id: String): Boolean {
        return userRepository.deleteUserById(id)
    }

    override fun deleteAll(): Boolean {
        userRepository.deleteAll()
        return true
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findUserByEmail(email)
    }

    override fun findByLogin(login: String): User? {
        return userRepository.findUserByLogin(login)
    }
}

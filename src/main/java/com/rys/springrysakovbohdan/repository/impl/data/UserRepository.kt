package com.rys.springrysakovbohdan.repository.impl.data

import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.repository.UserDAO
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User,ObjectId>,UserDAO {
    override fun getUserById(id: String): User?

    override fun getAllUsers(): List<User>

    override fun deleteUserById(id: String): Boolean
}

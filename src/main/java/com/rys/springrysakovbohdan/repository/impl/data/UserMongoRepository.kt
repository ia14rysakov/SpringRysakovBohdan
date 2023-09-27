package com.rys.springrysakovbohdan.repository.impl.data

import com.rys.springrysakovbohdan.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component

@Component
interface UserMongoRepository : MongoRepository<User, ObjectId> {
    fun deleteUserById(id: String): Boolean

    fun findUserById(id: String): User?

    fun findUserByEmail(email: String): User?

    fun findUserByLogin(login: String): User?


}

package com.rys.springrysakovbohdan.repository.impl.mongotemplate

import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.repository.UserDAO
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class UserMongoTemplate(private val mongoTemplate: MongoTemplate) : UserDAO {

    private val clazz = User::class.java

    override fun getUserById(id: String): User? = mongoTemplate.findById(id, clazz)

    override fun createUser(user: User): User = mongoTemplate.save(user)

    override fun getAllUsers(): List<User> = mongoTemplate.findAll(clazz)

    override fun deleteUserById(id: String): Boolean {
        val result = mongoTemplate.remove(Query(Criteria.where("_id").`is`(id)), clazz)
        return result.deletedCount > 0
    }
}

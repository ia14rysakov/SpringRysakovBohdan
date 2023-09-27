package com.rys.springrysakovbohdan.repository.impl.mongotemplate

import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.repository.UserDao
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class UserRepository(private val mongoTemplate: MongoTemplate) : UserDao {

    private val documentClass = User::class.java

    override fun findById(id: String): User? {
        TODO("Not yet implemented")
    }

    override fun create(user: User): User {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<User> {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: String): Boolean {
        val result = mongoTemplate.remove(Query(Criteria.where("_id").`is`(id)), documentClass)
        return result.deletedCount > 0
    }

    override fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }

    override fun findByEmail(email: String): User? {
        TODO("Not yet implemented")
    }

    override fun findByLogin(login: String): User? {
        TODO("Not yet implemented")
    }
}

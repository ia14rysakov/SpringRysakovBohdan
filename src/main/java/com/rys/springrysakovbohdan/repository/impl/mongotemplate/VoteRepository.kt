package com.rys.springrysakovbohdan.repository.impl.mongotemplate

import com.rys.springrysakovbohdan.model.Vote
import com.rys.springrysakovbohdan.repository.VoteDao
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class VoteRepository(private val mongoTemplate: MongoTemplate) : VoteDao {

    private val documentClass = Vote::class.java

    override fun findById(id: String): Vote? = mongoTemplate.findById(id, documentClass)

    override fun create(vote: Vote): Vote = mongoTemplate.save(vote)

    override fun findAll(): List<Vote> = mongoTemplate.findAll(documentClass)

    override fun deleteById(id: String): Boolean {
        val result = mongoTemplate.remove(Query(Criteria.where("_id").`is`(id)), documentClass)
        return result.deletedCount > 0
    }

    override fun deleteAll(): Boolean {
        val result = mongoTemplate.remove(Query(), documentClass)
        return result.deletedCount > 0
    }

    override fun findByUserId(id: String): List<Vote> {
        val query = Query(Criteria.where("userId").`is`(id))
        return mongoTemplate.find(query, documentClass)
    }
}

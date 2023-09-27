package com.rys.springrysakovbohdan.repository.impl.mongotemplate

import com.rys.springrysakovbohdan.model.Vote
import com.rys.springrysakovbohdan.repository.VoteDAO
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class VoteMongoTemplate(private val mongoTemplate: MongoTemplate) : VoteDAO {

    private val clazz = Vote::class.java

    override fun getVoteById(id: String): Vote? = mongoTemplate.findById(id, clazz)

    override fun createVote(vote: Vote): Vote = mongoTemplate.save(vote)

    override fun getAllVotes(): List<Vote> = mongoTemplate.findAll(clazz)

    override fun deleteVoteById(id: String): Boolean {
        val result = mongoTemplate.remove(Query(Criteria.where("_id").`is`(id)), clazz)
        return result.deletedCount > 0
    }
}

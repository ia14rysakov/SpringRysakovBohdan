package com.rys.springrysakovbohdan.repository.impl.data

import com.rys.springrysakovbohdan.model.Vote
import com.rys.springrysakovbohdan.repository.VoteDao
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
interface VoteMongoRepository : MongoRepository<Vote, ObjectId> {

    override fun findAll(): List<Vote>

    override fun deleteAll()

    fun findById(id: String): Vote?

    fun deleteVoteById(id: String): Boolean

    fun findVotesByUserId(userId: String) : List<Vote>

}

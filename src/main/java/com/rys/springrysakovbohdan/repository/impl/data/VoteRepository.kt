package com.rys.springrysakovbohdan.repository.impl.data

import com.rys.springrysakovbohdan.model.Vote
import com.rys.springrysakovbohdan.repository.VoteDAO
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface VoteRepository : MongoRepository<Vote, ObjectId>,VoteDAO {
    override fun getVoteById(id: String): Vote?

    override fun getAllVotes(): List<Vote>

    override fun deleteVoteById(id: String): Boolean
}

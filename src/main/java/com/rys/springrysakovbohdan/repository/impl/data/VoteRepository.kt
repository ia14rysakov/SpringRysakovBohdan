package com.rys.springrysakovbohdan.repository.impl.data

import com.rys.springrysakovbohdan.model.Vote
import com.rys.springrysakovbohdan.repository.VoteDao
import org.springframework.stereotype.Repository


@Repository
class VoteRepository(private val voteMongoRepository: VoteMongoRepository) : VoteDao {
    override fun findById(id: String): Vote? {
        return voteMongoRepository.findById(id)
    }

    override fun create(vote: Vote): Vote {
        return voteMongoRepository.save(vote)
    }

    override fun findAll(): List<Vote> {
        return voteMongoRepository.findAll()
    }

    override fun deleteById(id: String): Boolean {
        return voteMongoRepository.deleteVoteById(id)
    }

    override fun deleteAll(): Boolean {
        voteMongoRepository.deleteAll()
        return true
    }

    override fun findByUserId(id: String): List<Vote> {
        return voteMongoRepository.findVotesByUserId(id)
    }
}

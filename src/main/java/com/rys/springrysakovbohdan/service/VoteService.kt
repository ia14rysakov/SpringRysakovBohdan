package com.rys.springrysakovbohdan.service

import com.rys.springrysakovbohdan.model.Vote
import org.springframework.stereotype.Service

@Service
interface VoteService {

    fun createVote(vote: Vote, userId: String): Vote

    fun getVoteById(id: String): Vote?

    fun getAllVotes(): List<Vote>

    fun updateVote(voteId: String, vote: Vote, userId: String): Vote

    fun deleteVoteById(voteId: String,userId: String): Boolean

    fun deleteAllVotes(): Boolean
}

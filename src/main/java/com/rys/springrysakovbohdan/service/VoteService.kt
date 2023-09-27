package com.rys.springrysakovbohdan.service

import com.rys.springrysakovbohdan.model.Vote
import org.springframework.stereotype.Service

@Service
interface VoteService {

    fun createVote(vote: Vote): Vote

    fun getVoteById(id: String): Vote?

    fun getAllVotes(): List<Vote>

    fun updateVote(id: String, vote: Vote): Vote

    fun deleteVoteById(id: String): Boolean
}

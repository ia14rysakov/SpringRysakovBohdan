package com.rys.springrysakovbohdan.repository

import com.rys.springrysakovbohdan.model.Vote

interface VoteDAO {
    fun getVoteById(id: String): Vote?

    fun createVote(vote: Vote): Vote

    fun getAllVotes(): List<Vote>

    fun deleteVoteById(id: String): Boolean
}

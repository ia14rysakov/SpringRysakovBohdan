package com.rys.springrysakovbohdan.repository

import com.rys.springrysakovbohdan.model.Vote

interface VoteDao {
    fun findById(id: String): Vote?

    fun create(vote: Vote): Vote

    fun findAll(): List<Vote>

    fun deleteById(id: String): Boolean

    fun deleteAll() : Boolean

    fun findByUserId(id: String): List<Vote>

}

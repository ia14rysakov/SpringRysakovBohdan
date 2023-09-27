package com.rys.springrysakovbohdan.repository

import com.rys.springrysakovbohdan.model.Petition

interface PetitionDao {
    fun findById(id: String): Petition?

    fun create(petition: Petition): Petition

    fun findAll(): List<Petition>

    fun deleteById(id: String): Boolean

    fun deleteAll() : Boolean

    fun findByPetitionsUserId(id: String): List<Petition>


}

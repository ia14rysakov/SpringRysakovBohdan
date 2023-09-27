package com.rys.springrysakovbohdan.repository

import com.rys.springrysakovbohdan.model.Petition

interface PetitionDAO {
    fun getPetitionById(id: String): Petition?

    fun createPetition(petition: Petition): Petition

    fun getAllPetitions(): List<Petition>

    fun deletePetitionById(id: String): Boolean
}

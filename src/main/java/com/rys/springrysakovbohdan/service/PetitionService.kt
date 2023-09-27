package com.rys.springrysakovbohdan.service

import com.rys.springrysakovbohdan.model.Petition
import org.springframework.stereotype.Service

@Service
interface PetitionService {

    fun createPetition(petition: Petition): Petition

    fun getPetitionById(id: String): Petition?

    fun getAllPetitions(): List<Petition>

    fun updatePetition(id: String, petition: Petition): Petition

    fun deletePetitionById(id: String): Boolean
}

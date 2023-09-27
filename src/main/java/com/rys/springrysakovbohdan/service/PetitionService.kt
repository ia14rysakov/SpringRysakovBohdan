package com.rys.springrysakovbohdan.service

import com.rys.springrysakovbohdan.model.Petition
import org.springframework.stereotype.Service

@Service
interface PetitionService {

    fun createPetition(petition: Petition, userId: String): Petition

    fun getPetitionById(id: String): Petition?

    fun getAllPetitions(): List<Petition>

    fun updatePetition(petitionId: String, petition: Petition, userId: String): Petition

    fun deletePetitionById(petitionId: String, userId: String): Boolean

    fun deleteAllPetitions(): Boolean
}

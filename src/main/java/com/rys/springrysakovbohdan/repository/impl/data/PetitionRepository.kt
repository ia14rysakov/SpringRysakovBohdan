package com.rys.springrysakovbohdan.repository.impl.data

import com.rys.springrysakovbohdan.model.Petition
import com.rys.springrysakovbohdan.repository.PetitionDAO
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PetitionRepository : MongoRepository<Petition, ObjectId>, PetitionDAO {
    override fun getPetitionById(id: String): Petition?

    override fun getAllPetitions(): List<Petition>

    override fun deletePetitionById(id: String): Boolean
}

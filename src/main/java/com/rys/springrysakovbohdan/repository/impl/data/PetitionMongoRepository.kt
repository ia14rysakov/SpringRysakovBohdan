package com.rys.springrysakovbohdan.repository.impl.data

import com.rys.springrysakovbohdan.model.Petition
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PetitionMongoRepository : MongoRepository<Petition, ObjectId> {
    fun getPetitionById(id: String): Petition?

    fun deletePetitionById(id: String): Boolean

    fun findAllByUserId(id: String): List<Petition>
}

package com.rys.springrysakovbohdan.repository.impl.mongotemplate

import com.rys.springrysakovbohdan.model.Petition
import com.rys.springrysakovbohdan.repository.PetitionDAO
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class PetitionMongoTemplate(private val mongoTemplate: MongoTemplate) : PetitionDAO {

    private val clazz = Petition::class.java

    override fun getPetitionById(id: String): Petition? =
        mongoTemplate.findById(id, clazz)


    override fun createPetition(petition: Petition): Petition = mongoTemplate.save(petition)

    override fun getAllPetitions(): List<Petition> = mongoTemplate.findAll(clazz)

    override fun deletePetitionById(id: String): Boolean {
        val result = mongoTemplate.remove(Query(Criteria.where("_id").`is`(id)), clazz)
        return result.deletedCount > 0
    }
}

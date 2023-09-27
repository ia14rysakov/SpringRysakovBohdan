package com.rys.springrysakovbohdan.repository.impl.mongotemplate

import com.rys.springrysakovbohdan.model.Petition
import com.rys.springrysakovbohdan.repository.PetitionDao
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class PetitionRepository(private val mongoTemplate: MongoTemplate) : PetitionDao {

    private val documentClass = Petition::class.java

    override fun findById(id: String): Petition? =
        mongoTemplate.findById(id, documentClass)


    override fun create(petition: Petition): Petition = mongoTemplate.save(petition)

    override fun findAll(): List<Petition> = mongoTemplate.findAll(documentClass)
    override fun deleteById(id: String): Boolean {
        val result = mongoTemplate.remove(Query(Criteria.where("_id").`is`(id)), documentClass)
        return result.deletedCount > 0
    }

    override fun deleteAll(): Boolean {
        val result = mongoTemplate.remove(Query(), documentClass)
        return result.deletedCount > 0
    }

    override fun findByPetitionsUserId(id: String): List<Petition> {
        val query = Query(Criteria.where("userId").`is`(id))
        return mongoTemplate.find(query, documentClass)
    }

}

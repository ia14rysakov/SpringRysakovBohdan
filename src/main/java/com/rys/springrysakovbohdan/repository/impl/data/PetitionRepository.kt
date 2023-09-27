package com.rys.springrysakovbohdan.repository.impl.data

import com.rys.springrysakovbohdan.model.Petition
import com.rys.springrysakovbohdan.repository.PetitionDao
import org.springframework.stereotype.Repository

@Repository
class PetitionRepository(private val petitionRepository: PetitionMongoRepository) : PetitionDao {
    override fun findById(id: String): Petition? {
        return petitionRepository.getPetitionById(id)
    }

    override fun create(petition: Petition): Petition {
        return petitionRepository.save(petition)
    }

    override fun findAll(): List<Petition> {
        return petitionRepository.findAll()
    }

    override fun deleteById(id: String): Boolean {
        return petitionRepository.deletePetitionById(id)
    }

    override fun deleteAll(): Boolean {
        petitionRepository.deleteAll()
        return true
    }

    override fun findByPetitionsUserId(id: String): List<Petition> {
        return petitionRepository.findAllByUserId(id)
    }
}

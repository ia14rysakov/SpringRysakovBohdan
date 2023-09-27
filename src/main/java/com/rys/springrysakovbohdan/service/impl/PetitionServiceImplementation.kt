package com.rys.springrysakovbohdan.service.impl

import com.rys.springrysakovbohdan.exceptions.PetitionNotFoundException
import com.rys.springrysakovbohdan.model.Petition
import com.rys.springrysakovbohdan.repository.PetitionDAO
import com.rys.springrysakovbohdan.service.PetitionService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
@Suppress("TooGenericExceptionCaught")
class PetitionServiceImplementation(val petitionRepository: PetitionDAO) : PetitionService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun createPetition(petition: Petition): Petition =
        try {
            logger.info("Attempt to create Petition")
            val newPetition = petitionRepository.createPetition(petition)
            logger.info("Petition {} Succesfully Created", newPetition)
            newPetition
        } catch (e: Exception) {
            logger.error("Petition {} Failed To Create", petition, e)
            throw e
        }

    override fun getPetitionById(id: String): Petition? =
        try {
            logger.info("Attempt to get Petition by Id")
            val petition = petitionRepository.getPetitionById(id)
            logger.info("Petition {} Succesfully Found", id)
            petition
        } catch (e: Exception) {
            logger.error("Petition {} not found", id, e)
            throw e
        }

    override fun getAllPetitions(): List<Petition> =
        run {
            logger.info("Attempt to get all Petitions")
            val petition = petitionRepository.getAllPetitions()
            petition.ifEmpty {
                logger.warn("Petitions Are Not Found! Maybe database is empty?")
            }
            logger.info("Petitions loaded from DB")
            petition
        }

    override fun updatePetition(id: String, petition: Petition): Petition =
        run {
            logger.info("Attempt to update petition {} with data : {}", id, petition)
            getPetitionById(id)
                ?.let {
                    logger.info("Petition with id : {} found! Making attempt to update data", id)
                    try {
                        val newPetition = petitionRepository.createPetition(petition)
                        logger.info(
                            "Petition with id : {} succesfully updated. Id after updation : {}",
                            id,
                            newPetition.id
                        )
                        newPetition
                    } catch (e: Exception) {
                        logger.error(
                            "Updation failed! Failed to save new petition! Petition id : {}, new data : {}",
                            id,
                            petition
                        )
                        throw e
                    }
                }
                ?: run {
                    logger.error("Updation Failed! Petition {} Not Found", id)
                    throw PetitionNotFoundException()
                }
        }

    override fun deletePetitionById(id: String): Boolean =
        run {
            logger.info("Attempt to delete petition {}", id)
            getPetitionById(id)
                ?.let {
                    logger.info("Petition with id : {} found! Making attempt to delete", id)
                    try {
                        val result = petitionRepository.deletePetitionById(id)
                        logger.info("Petition with id : {} succesfully deleted", id)
                        result
                    } catch (e: Exception) {
                        logger.error("Deletion failed! Failed to delete petition! Petition id : {}", id, e)
                        throw e
                    }
                }
                ?: run {
                    logger.error("Updation Failed! Petition {} Not Found", id)
                    throw PetitionNotFoundException()
                }
        }
}

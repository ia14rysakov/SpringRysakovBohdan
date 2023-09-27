package com.rys.springrysakovbohdan.service.impl

import com.rys.springrysakovbohdan.exceptions.PetitionNotFoundException
import com.rys.springrysakovbohdan.exceptions.UnauthorizedAccessException
import com.rys.springrysakovbohdan.model.Petition
import com.rys.springrysakovbohdan.repository.PetitionDao
import com.rys.springrysakovbohdan.service.PetitionService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
@Suppress("TooGenericExceptionCaught")
class PetitionServiceImplementation(val petitionRepository: PetitionDao) : PetitionService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun createPetition(petition: Petition, userId: String): Petition =
        try {
            logger.info("User {} attempting to create a new petition", userId)
            val newPetition = petitionRepository.create(petition.copy(userId = userId))
            logger.info("Successfully created petition {} for user {}", newPetition, userId)
            newPetition
        } catch (e: Exception) {
            logger.error("Failed to create petition for user {}: {}", userId, petition, e)
            throw e
        }

    override fun getPetitionById(id: String): Petition? =
        try {
            logger.info("Attempting to retrieve petition with ID: {}", id)
            val petition = petitionRepository.findById(id)
            if (petition != null) {
                logger.info("Successfully retrieved petition with ID: {}", id)
            } else {
                logger.warn("Petition with ID {} not found", id)
            }
            petition
        } catch (e: Exception) {
            logger.error("Error occurred while retrieving petition with ID: {}", id, e)
            throw e
        }

    override fun getAllPetitions(): List<Petition> =
        run {
            logger.info("Attempting to retrieve all petitions")
            val petitionList = petitionRepository.findAll()
            if (petitionList.isEmpty()) {
                logger.warn("No petitions found in the database")
            } else {
                logger.info("Successfully retrieved {} petitions from the database", petitionList.size)
            }
            petitionList
        }

    override fun updatePetition(petitionId: String, petition: Petition, userId: String): Petition =
        run {
            logger.info("User {} attempting to update petition with ID: {}", userId, petitionId)
            val existingPetition = getPetitionById(petitionId) ?: run {
                logger.warn("Petition with ID {} not found during update attempt", petitionId)
                throw PetitionNotFoundException()
            }

            if (existingPetition.userId != userId) {
                logger.warn("Unauthorized attempt by user {} to update petition {}", userId, petitionId)
                throw UnauthorizedAccessException("User not authorized to update this petition")
            }

            try {
                val updatedPetition =
                    petitionRepository.create(petition.copy(id = existingPetition.id, userId = userId))
                logger.info(
                    "User {} successfully updated petition with ID {}. New ID after update: {}",
                    userId, petitionId, updatedPetition.id
                )
                updatedPetition
            } catch (e: Exception) {
                logger.error("Failed to update petition with ID {} for user {}: {}", petitionId, userId, petition, e)
                throw e
            }
        }

    override fun deletePetitionById(petitionId: String, userId: String): Boolean =
        run {
            logger.info("User {} attempting to delete petition with ID: {}", userId, petitionId)
            val existingPetition = getPetitionById(petitionId) ?: run {
                logger.warn("Petition with ID {} not found during deletion attempt", petitionId)
                throw PetitionNotFoundException()
            }

            if (existingPetition.userId != userId) {
                logger.warn("Unauthorized attempt by user {} to delete petition {}", userId, petitionId)
                throw UnauthorizedAccessException("User not authorized to delete this petition")
            }

            try {
                val result = petitionRepository.deleteById(petitionId)
                logger.info("User {} successfully deleted petition with ID: {}", userId, petitionId)
                result
            } catch (e: Exception) {
                logger.error("Failed to delete petition with ID {} for user {}: ", petitionId, userId, e)
                throw e
            }
        }
}

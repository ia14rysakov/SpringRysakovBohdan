package com.rys.springrysakovbohdan.controller

import com.rys.springrysakovbohdan.model.Petition
import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.service.PetitionService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/petition")
class PetitionController(private val petitionService: PetitionService) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/")
    fun createPetition(@Valid @RequestBody petition: Petition, principal: Principal): ResponseEntity<Petition> {
        try {
            logger.info("User ${principal.name} is attempting to create a new petition")
            val newPetition = petitionService.createPetition(petition, (principal as User).id!!)
            logger.info("Successfully created petition ${newPetition.id} for user ${principal.name}")
            return ResponseEntity(newPetition, HttpStatus.CREATED)
        } catch (e: Exception) {
            logger.error("Error occurred while user ${principal.name} tried to create a petition", e)
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getPetitionById(@PathVariable id: String): ResponseEntity<Petition> {
        return petitionService.getPetitionById(id)?.let {
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/")
    fun getAllPetitions(): ResponseEntity<List<Petition>> {
        val petitions = petitionService.getAllPetitions()
        return if (petitions.isNotEmpty()) {
            ResponseEntity(petitions, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }

    @PutMapping("/{id}")
    fun updatePetition(
        @PathVariable id: String,
        @Valid @RequestBody updatedPetition: Petition,
        principal: Principal
    ): ResponseEntity<Petition> {
        try {
            logger.info("User ${principal.name} is attempting to update petition $id")
            val updated = petitionService.updatePetition(id, updatedPetition, (principal as User).id!!)
            logger.info("Successfully updated petition $id for user ${principal.name}")
            return ResponseEntity(updated, HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("Error occurred while user ${principal.name} tried to update petition $id", e)
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/{id}")
    fun deletePetition(@PathVariable id: String, principal: Principal): ResponseEntity<Void> {
        try {
            logger.info("User ${principal.name} is attempting to delete petition $id")
            petitionService.deletePetitionById(id, (principal as User).id!!)
            logger.info("Successfully deleted petition $id for user ${principal.name}")
            return ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception) {
            logger.error("Error occurred while user ${principal.name} tried to delete petition $id", e)
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}





package com.rys.springrysakovbohdan.controller

import com.rys.springrysakovbohdan.model.Petition
import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.service.PetitionService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.security.Principal

@Controller
@RequestMapping("/petition")
@Suppress("TooGenericExceptionCaught")
class PetitionController(private val petitionService: PetitionService) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/")
    fun createPetition(@Valid @ModelAttribute petition: Petition, principal: Principal, model: ModelMap): ModelAndView {
        return try {
            logger.info("User ${principal.name} is attempting to create a new petition")
            val newPetition = petitionService.createPetition(petition, (principal as User).id!!)
            logger.info("Successfully created petition ${newPetition.id} for user ${principal.name}")
            model.addAttribute("petition", newPetition)
            ModelAndView("petitionDetails", model)
        } catch (e: Exception) {
            logger.error("Error occurred while user ${principal.name} tried to create a petition", e)
            ModelAndView("errorPage") // This can be a generic error page
        }
    }

    @GetMapping("/{id}")
    fun getPetitionById(@PathVariable id: String, model: ModelMap): ModelAndView {
        return petitionService.getPetitionById(id)?.let {
            model.addAttribute("petition", it)
            ModelAndView("petitionDetails", model)
        } ?: ModelAndView("notFoundPage") // This can be a generic not found page
    }

    @GetMapping("/")
    fun getAllPetitions(model: ModelMap): ModelAndView {
        val petitions = petitionService.getAllPetitions()
        model.addAttribute("petitions", petitions)
        return if (petitions.isNotEmpty()) {
            ModelAndView("petitionsList", model)
        } else {
            ModelAndView("emptyPetitionsListPage", model) // This can be a generic empty list page
        }
    }

    @PutMapping("/{id}")
    fun updatePetition(
        @PathVariable id: String,
        @Valid @ModelAttribute updatedPetition: Petition,
        principal: Principal,
        model: ModelMap
    ): ModelAndView {
        return try {
            logger.info("User ${principal.name} is attempting to update petition $id")
            val updated = petitionService.updatePetition(id, updatedPetition, (principal as User).id!!)
            logger.info("Successfully updated petition $id for user ${principal.name}")
            model.addAttribute("petition", updated)
            ModelAndView("petitionDetails", model)
        } catch (e: Exception) {
            logger.error("Error occurred while user ${principal.name} tried to update petition $id", e)
            ModelAndView("errorPage")
        }
    }

    @DeleteMapping("/{id}")
    fun deletePetition(@PathVariable id: String, principal: Principal): ModelAndView {
        return try {
            logger.info("User ${principal.name} is attempting to delete petition $id")
            petitionService.deletePetitionById(id, (principal as User).id!!)
            logger.info("Successfully deleted petition $id for user ${principal.name}")
            ModelAndView("petitionDeletedSuccessPage") // This can be a success page after deleting a petition
        } catch (e: Exception) {
            logger.error("Error occurred while user ${principal.name} tried to delete petition $id", e)
            ModelAndView("errorPage")
        }
    }
}

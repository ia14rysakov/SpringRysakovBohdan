package com.rys.springrysakovbohdan.controller

import com.rys.springrysakovbohdan.model.Petition
import com.rys.springrysakovbohdan.service.PetitionService
import jakarta.validation.Valid
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


@RestController
@RequestMapping("/petition")
class PetitionController(private val petitionService: PetitionService) {

    @PostMapping("/")
    fun createPetition(@Valid @RequestBody petition: Petition): ResponseEntity<Petition> =
        ResponseEntity(petitionService.createPetition(petition), HttpStatus.CREATED)


    @GetMapping("/{id}")
    fun getPetitionById(@PathVariable id: String): ResponseEntity<Petition> =
        ResponseEntity(petitionService.getPetitionById(id), HttpStatus.OK)


    @GetMapping("/")
    fun getAllPetitions(): ResponseEntity<List<Petition>> =
        ResponseEntity(petitionService.getAllPetitions(), HttpStatus.OK)


    @PutMapping("/{id}")
    fun updatePetition(
        @PathVariable id: String,
        @Valid @RequestBody updatedPetition: Petition
    ): ResponseEntity<Petition> = ResponseEntity(petitionService.updatePetition(id, updatedPetition), HttpStatus.OK)


    @DeleteMapping("/{id}")
    fun deletePetition(@PathVariable id: String): ResponseEntity<Void> {
        petitionService.deletePetitionById(id)
        return ResponseEntity(HttpStatus.OK)
    }
}

package com.rys.springrysakovbohdan.controller

import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.model.Vote
import com.rys.springrysakovbohdan.service.VoteService
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
@RequestMapping("/vote")
@Suppress("TooGenericExceptionCaught")
class VoteController(private val voteService: VoteService) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/")
    fun createVote(@Valid @RequestBody vote: Vote, principal: Principal): ResponseEntity<Vote> {
        try {
            logger.info("User ${principal.name} is attempting to cast a vote")
            val newVote = voteService.createVote(vote, (principal as User).id!!)
            logger.info("Vote ${newVote.id} successfully cast by user ${principal.name}")
            return ResponseEntity(newVote, HttpStatus.CREATED)
        } catch (e: Exception) {
            logger.error("Error occurred while user ${principal.name} tried to cast a vote", e)
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getVoteById(@PathVariable id: String): ResponseEntity<Vote> {
        return voteService.getVoteById(id)?.let {
            logger.info("Fetched vote with id: $id")
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/")
    fun getAllVotes(): ResponseEntity<List<Vote>> {
        val votes = voteService.getAllVotes()
        if (votes.isNotEmpty()) {
            logger.info("Fetched all votes from database")
            return ResponseEntity(votes, HttpStatus.OK)
        } else {
            logger.warn("No votes found in the database")
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }

    @PutMapping("/{id}")
    fun updateVote(
        @PathVariable id: String,
        @Valid @RequestBody updatedVote: Vote,
        principal: Principal
    ): ResponseEntity<Vote> {
        try {
            logger.info("User ${principal.name} is attempting to update vote with id: $id")
            val vote = voteService.updateVote(id, updatedVote, (principal as User).id!!)
            logger.info("Successfully updated vote with id: $id by user ${principal.name}")
            return ResponseEntity(vote, HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("Error occurred while user ${principal.name} tried to update vote with id: $id", e)
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteVote(@PathVariable id: String, principal: Principal): ResponseEntity<Void> {
        try {
            logger.info("User ${principal.name} is attempting to delete vote with id: $id")
            voteService.deleteVoteById(id, (principal as User).id!!)
            logger.info("Successfully deleted vote with id: $id by user ${principal.name}")
            return ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception) {
            logger.error("Error occurred while user ${principal.name} tried to delete vote with id: $id", e)
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}


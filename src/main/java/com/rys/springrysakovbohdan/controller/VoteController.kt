package com.rys.springrysakovbohdan.controller

import com.rys.springrysakovbohdan.model.Vote
import com.rys.springrysakovbohdan.service.VoteService
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
@RequestMapping("/vote")
class VoteController(private val voteService: VoteService) {

    @PostMapping("/")
    fun createVote(@Valid @RequestBody vote: Vote): ResponseEntity<Vote> =
        ResponseEntity(voteService.createVote(vote), HttpStatus.CREATED)

    @GetMapping("/{id}")
    fun getVoteById(@PathVariable id: String): ResponseEntity<Vote> =
        ResponseEntity(voteService.getVoteById(id), HttpStatus.OK)


    @GetMapping("/")
    fun getAllVotes(): ResponseEntity<List<Vote>> =
        ResponseEntity(voteService.getAllVotes(), HttpStatus.OK)

    @PutMapping("/{id}")
    fun updateVote(
        @PathVariable id: String,
        @Valid @RequestBody updatedVote: Vote
    ): ResponseEntity<Vote> = ResponseEntity(voteService.updateVote(id, updatedVote), HttpStatus.OK)

    @DeleteMapping("/{id}")
    fun deleteVote(@PathVariable id: String): ResponseEntity<Void> {
        voteService.deleteVoteById(id)
        return ResponseEntity(HttpStatus.OK)
    }
}

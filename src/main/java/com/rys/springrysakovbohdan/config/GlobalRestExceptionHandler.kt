package com.rys.springrysakovbohdan.config

import com.rys.springrysakovbohdan.exceptions.PetitionNotFoundException
import com.rys.springrysakovbohdan.exceptions.UserNotFoundException
import com.rys.springrysakovbohdan.exceptions.VoteNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalRestExceptionHandler {
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(): ResponseEntity<Any> {
        val body = mapOf(
            "message" to "User Not Found",
            "status" to HttpStatus.NOT_FOUND.value()
        )
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(PetitionNotFoundException::class)
    fun handlePetitionNotFound(): ResponseEntity<Any> {
        val body = mapOf(
            "message" to "Petition Not Found",
            "status" to HttpStatus.NOT_FOUND.value()
        )
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(VoteNotFoundException::class)
    fun handleVoteNotFound(): ResponseEntity<Any> {
        val body = mapOf(
            "message" to "Vote Not Found",
            "status" to HttpStatus.NOT_FOUND.value()
        )
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }
}

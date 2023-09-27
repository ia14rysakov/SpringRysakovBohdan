package com.rys.springrysakovbohdan.model


import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "USERS")
data class User(
    val id: String? = null,
    @field:NotNull(message = "User's name cant be null!")
    @field:Size(min = 5, message = "User's name must be at least 5 symbols light")
    val username: String,
    @field:NotNull(message = "User's password cant be null!")
    @field:Size(min = 9, message = "User's password must be at least 8 symbols light")
    val password: String,
    @field:Email(message = "Wrong email!")
    val email: String,
    val createdPetitions: MutableList<String> = mutableListOf(),
    val votedPetitions: MutableList<String> = mutableListOf()
)

package com.rys.springrysakovbohdan.model



import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "PETITIONS")
data class Petition(
    val id: String? = null,
    @field:NotNull
    @field:Size(min = 5, message = "Petition title must be at least 5 symbols length!")
    val title: String,
    @field:NotNull
    @field:Size(min = 30, message = "Petition description must be at least 30 symbols length!")
    val description: String,
    @field:NotBlank(message = "Petition must have author!")
    val createdBy: String,
    var votes: Int = 0,
    @field:NotBlank(message = "Petition must have link!")
    val url: String
)

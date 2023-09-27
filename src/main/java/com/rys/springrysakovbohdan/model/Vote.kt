package com.rys.springrysakovbohdan.model


import jakarta.validation.constraints.NotNull
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "VOTES")
data class Vote(
    val id: String? = null,
    @field:NotNull(message = "Vote must me associated with petition")
    val petitionId: String,
    @field:NotNull(message = "Vote must me associated with user")
    val userId: String,
    val voteTimestamp: LocalDateTime = LocalDateTime.now()
)

package com.rys.springrysakovbohdan.service.impl

import com.rys.springrysakovbohdan.exceptions.UnauthorizedAccessException
import com.rys.springrysakovbohdan.exceptions.VoteNotFoundException
import com.rys.springrysakovbohdan.model.Vote
import com.rys.springrysakovbohdan.repository.VoteDao
import com.rys.springrysakovbohdan.service.VoteService

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Suppress("TooGenericExceptionCaught")
class VoteServiceImplementation(val voteRepository: VoteDao) : VoteService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun createVote(vote: Vote, userId: String): Vote =
        try {
            logger.info("User {} attempting to create a new vote", userId)
            val newVote = voteRepository.create(vote.copy(userId = userId))
            logger.info("Successfully created vote {} for user {}", newVote, userId)
            newVote
        } catch (e: Exception) {
            logger.error("Failed to create vote for user {}: {}", userId, vote, e)
            throw e
        }

    override fun getVoteById(id: String): Vote? =
        try {
            logger.info("Attempting to retrieve vote with ID: {}", id)
            val vote = voteRepository.findById(id)
            if (vote != null) {
                logger.info("Successfully retrieved vote with ID: {}", id)
            } else {
                logger.warn("Vote with ID {} not found", id)
            }
            vote
        } catch (e: Exception) {
            logger.error("Error occurred while retrieving vote with ID: {}", id, e)
            throw e
        }

    override fun getAllVotes(): List<Vote> =
        run {
            logger.info("Attempting to retrieve all votes")
            val voteList = voteRepository.findAll()
            if (voteList.isEmpty()) {
                logger.warn("No votes found in the database")
            } else {
                logger.info("Successfully retrieved {} votes from the database", voteList.size)
            }
            voteList
        }

    override fun updateVote(voteId: String, vote: Vote, userId: String): Vote =
        run {
            logger.info("User {} attempting to update vote with ID: {}", userId, voteId)
            val existingVote = getVoteById(voteId) ?: run {
                logger.warn("Vote with ID {} not found during update attempt", voteId)
                throw VoteNotFoundException()
            }

            // Assuming votes are tied to users in your business logic
            if (existingVote.userId != userId) {
                logger.warn("Unauthorized attempt by user {} to update vote {}", userId, voteId)
                throw UnauthorizedAccessException("User not authorized to update this vote")
            }

            try {
                val updatedVote = voteRepository.create(vote.copy(id = existingVote.id, userId = userId))
                logger.info(
                    "User {} successfully updated vote with ID {}. New ID after update: {}",
                    userId,
                    voteId,
                    updatedVote.id
                )
                updatedVote
            } catch (e: Exception) {
                logger.error("Failed to update vote with ID {} for user {}: {}", voteId, userId, vote, e)
                throw e
            }
        }

    override fun deleteVoteById(voteId: String, userId: String): Boolean =
        run {
            logger.info("User {} attempting to delete vote with ID: {}", userId, voteId)
            val existingVote = getVoteById(voteId) ?: run {
                logger.warn("Vote with ID {} not found during deletion attempt", voteId)
                throw VoteNotFoundException()
            }

            // Assuming votes are tied to users in your business logic
            if (existingVote.userId != userId) {
                logger.warn("Unauthorized attempt by user {} to delete vote {}", userId, voteId)
                throw UnauthorizedAccessException("User not authorized to delete this vote")
            }

            try {
                val result = voteRepository.deleteById(voteId)
                logger.info("User {} successfully deleted vote with ID: {}", userId, voteId)
                result
            } catch (e: Exception) {
                logger.error("Failed to delete vote with ID {} for user {}: ", voteId, userId, e)
                throw e
            }
        }
}

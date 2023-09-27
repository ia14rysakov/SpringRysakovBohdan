package com.rys.springrysakovbohdan.service.impl

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

    override fun createVote(vote: Vote): Vote =
        try {
            logger.info("Attempt to create Vote")
            val newVote = voteRepository.create(vote)
            logger.info("Vote {} Succesfully Created", newVote)
            newVote
        } catch (e: Exception) {
            logger.error("Vote {} Failed To Create", vote, e)
            throw e
        }

    override fun getVoteById(id: String): Vote? =
        try {
            logger.info("Attempt to get Vote by Id")
            val vote = voteRepository.findById(id)
            logger.info("Vote {} Succesfully Found", id)
            vote
        } catch (e: Exception) {
            logger.error("Vote {} not found", id, e)
            throw e
        }

    override fun getAllVotes(): List<Vote> =
        run {
            logger.info("Attempt to get all Votes")
            val votes = voteRepository.findAll()
            votes.ifEmpty {
                logger.warn("Votes Are Not Found! Maybe database is empty?")
            }
            logger.info("Votes loaded from DB")
            votes
        }

    override fun updateVote(id: String, vote: Vote): Vote =
        run {
            logger.info("Attempt to update Vote {} with data {} ", id, vote)
            getVoteById(id)
                ?.let {
                    logger.info("Vote with id : {} found! Making attempt to update data", id)
                    try {
                        val newVote = voteRepository.create(vote)
                        logger.info("Vote with id  {} Succesfully Updated. Id after updation : {}", id, newVote.id)
                        newVote
                    } catch (e: Exception) {
                        logger.error("Failed to update Vote {}", id, e)
                        throw e
                    }
                }
                ?: run {
                    logger.error("Vote Not Found {}", vote.id)
                    throw VoteNotFoundException()
                }
        }

    override fun deleteVoteById(id: String): Boolean =
        run {
            logger.info("Attempt to delete Vote {}", id)
            getVoteById(id)
                ?.let {
                    logger.info("Vote with id : {} found! Making attempt to delete", id)
                    try {
                        val result = voteRepository.deleteById(id)
                        logger.info("Vote {} Succesfully Deleted", id)
                        result
                    } catch (e: Exception) {
                        logger.error("Failed to delete Vote {}", id, e)
                        throw e
                    }
                }
                ?: run {
                    logger.error("Vote Not Found {}", id)
                    throw VoteNotFoundException()
                }
        }
}


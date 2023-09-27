package com.rys.springrysakovbohdan.service.impl

import com.rys.springrysakovbohdan.exceptions.UserNotFoundException
import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.repository.UserDao
import com.rys.springrysakovbohdan.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Suppress("TooGenericExceptionCaught")
class UserServiceImplementation(val userRepository: UserDao) : UserService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun createUser(user: User): User =
        try {
            logger.info("Attempt to create User")
            val newUser = userRepository.create(user)
            logger.info("User {} Succesfully Created", newUser)
            newUser
        } catch (e: Exception) {
            logger.error("User {} Failed To Create", user, e)
            throw e
        }

    override fun getUserById(id: String): User? =
        try {
            logger.info("Attempt to get User by Id")
            val user = userRepository.findById(id)
            logger.info("User {} Succesfully Found", id)
            user
        } catch (e: Exception) {
            logger.error("User {} not found", id, e)
            throw e
        }

    override fun getAllUsers(): List<User> =
        run {
            logger.info("Attempt to get all Users")
            val users = userRepository.findAll()
            users.ifEmpty {
                logger.warn("Users Are Not Found! Maybe database is empty?")
            }
            logger.info("Users loaded from DB")
            users
        }

    override fun updateUserById(targetId: String, user: User, currentId : String): User =
        run {
            logger.info("Attempt to update User {} with data : {}", targetId, user)
            getUserById(targetId)
                ?.let {
                    logger.info("User with id : {} found! Making attempt to update data", targetId)
                    try {
                        val updatedUser = userRepository.create(user)
                        logger.info("User {} Succesfully Updated. Id after updation : {} ", targetId, updatedUser.id)
                        updatedUser
                    } catch (e: Exception) {
                        logger.error("Failed to update User {}", targetId, e)
                        throw e
                    }
                }
                ?: run {
                    logger.error("User Not Found {}", targetId)
                    throw UserNotFoundException()
                }
        }

    override fun deleteUserById(targetId: String, currentId: String): Boolean =
        run {
            logger.info("Attempt to delete User {}", targetId)
            getUserById(targetId)
                ?.let {
                    logger.info("User with id : {} found! Making attempt to delete", targetId)
                    try {
                        val result = userRepository.deleteById(targetId)
                        logger.info("User {} Succesfully Deleted", targetId)
                        result
                    } catch (e: Exception) {
                        logger.error("Failed to delete User {}", targetId, e)
                        throw e
                    }
                } ?: run {
                logger.error("User Not Found {}", targetId)
                throw UserNotFoundException()
            }
        }
}

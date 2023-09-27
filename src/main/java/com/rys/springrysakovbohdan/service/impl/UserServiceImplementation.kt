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

    override fun updateUserById(id: String, user: User): User =
        run {
            logger.info("Attempt to update User {} with data : {}", id, user)
            getUserById(id)
                ?.let {
                    logger.info("User with id : {} found! Making attempt to update data", id)
                    try {
                        val updatedUser = userRepository.create(user)
                        logger.info("User {} Succesfully Updated. Id after updation : {} ", id, updatedUser.id)
                        updatedUser
                    } catch (e: Exception) {
                        logger.error("Failed to update User {}", id, e)
                        throw e
                    }
                }
                ?: run {
                    logger.error("User Not Found {}", id)
                    throw UserNotFoundException()
                }
        }

    override fun deleteUserById(id: String): Boolean =
        run {
            logger.info("Attempt to delete User {}", id)
            getUserById(id)
                ?.let {
                    logger.info("User with id : {} found! Making attempt to delete", id)
                    try {
                        val result = userRepository.deleteById(id)
                        logger.info("User {} Succesfully Deleted", id)
                        result
                    } catch (e: Exception) {
                        logger.error("Failed to delete User {}", id, e)
                        throw e
                    }
                } ?: run {
                logger.error("User Not Found {}", id)
                throw UserNotFoundException()
            }
        }
}

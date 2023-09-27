package com.rys.springrysakovbohdan.controller

import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.service.UserService
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
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/")
    fun createUser(@Valid @RequestBody user: User): ResponseEntity<User> {
        return try {
            logger.info("Attempting to create a new user with username: ${user.login}")
            val newUser = userService.createUser(user)
            logger.info("Successfully created user with id: ${newUser.id}")
            ResponseEntity(newUser, HttpStatus.CREATED)
        } catch (e: Exception) {
            logger.error("Error occurred while trying to create user with username: ${user.login}", e)
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<User> {
        return userService.getUserById(id)?.let {
            logger.info("Fetched user with id: $id")
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/")
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        if (users.isNotEmpty()) {
            logger.info("Fetched all users from database")
            return ResponseEntity(users, HttpStatus.OK)
        } else {
            logger.warn("No users found in the database")
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: String,
        @Valid @RequestBody updatedUser: User,
        principal: Principal
    ): ResponseEntity<User> {
        return try {
            logger.info("Attempting to update user with id: $id")
            val user = userService.updateUserById(id, updatedUser,(principal as User).id!!)
            logger.info("Successfully updated user with id: $id")
            ResponseEntity(user, HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("Error occurred while trying to update user with id: $id", e)
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String, principal: Principal): ResponseEntity<Void> {
        return try {
            logger.info("Attempting to delete user with id: $id")
            userService.deleteUserById(id,(principal as User).id!!)
            logger.info("Successfully deleted user with id: $id")
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception) {
            logger.error("Error occurred while trying to delete user with id: $id", e)
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}


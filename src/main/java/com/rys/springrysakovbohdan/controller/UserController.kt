package com.rys.springrysakovbohdan.controller

import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.service.UserService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import java.security.Principal

@Controller
@RequestMapping("/user")
@Suppress("TooGenericExceptionCaught")
class UserController(private val userService: UserService) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/")
    fun createUser(@Valid @RequestBody user: User): ModelAndView {
        val mav = ModelAndView()
        try {
            logger.info("Attempting to create a new user with username: ${user.login}")
            val newUser = userService.createUser(user)
            logger.info("Successfully created user with id: ${newUser.id}")
            mav.addObject("user", newUser)
            mav.status = HttpStatus.CREATED
        } catch (e: Exception) {
            logger.error("Error occurred while trying to create user with username: ${user.login}", e)
            mav.status = HttpStatus.INTERNAL_SERVER_ERROR
        }
        return mav
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): ModelAndView {
        val mav = ModelAndView()
        userService.getUserById(id)?.let {
            logger.info("Fetched user with id: $id")
            mav.addObject("user", it)
            mav.status = HttpStatus.OK
        } ?: run {
            mav.status = HttpStatus.NOT_FOUND
        }
        return mav
    }

    @GetMapping("/")
    fun getAllUsers(): ModelAndView {
        val mav = ModelAndView()
        val users = userService.getAllUsers()
        if (users.isNotEmpty()) {
            logger.info("Fetched all users from database")
            mav.addObject("users", users)
            mav.status = HttpStatus.OK
        } else {
            logger.warn("No users found in the database")
            mav.status = HttpStatus.NO_CONTENT
        }
        return mav
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: String,
        @Valid @RequestBody updatedUser: User,
        principal: Principal
    ): ModelAndView {
        val mav = ModelAndView()
        try {
            logger.info("Attempting to update user with id: $id")
            val user = userService.updateUserById(id, updatedUser, (principal as User).id!!)
            logger.info("Successfully updated user with id: $id")
            mav.addObject("user", user)
            mav.status = HttpStatus.OK
        } catch (e: Exception) {
            logger.error("Error occurred while trying to update user with id: $id", e)
            mav.status = HttpStatus.INTERNAL_SERVER_ERROR
        }
        return mav
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String, principal: Principal): ModelAndView {
        val mav = ModelAndView()
        try {
            logger.info("Attempting to delete user with id: $id")
            userService.deleteUserById(id, (principal as User).id!!)
            logger.info("Successfully deleted user with id: $id")
            mav.status = HttpStatus.NO_CONTENT
        } catch (e: Exception) {
            logger.error("Error occurred while trying to delete user with id: $id", e)
            mav.status = HttpStatus.INTERNAL_SERVER_ERROR
        }
        return mav
    }
}

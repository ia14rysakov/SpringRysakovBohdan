package com.rys.springrysakovbohdan.controller

import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.service.UserService
import jakarta.validation.Valid
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

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/")
    fun createUser(@Valid @RequestBody user: User): ResponseEntity<User> =
        ResponseEntity(userService.createUser(user), HttpStatus.CREATED)

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<User> =
        ResponseEntity(userService.getUserById(id), HttpStatus.OK)


    @GetMapping("/")
    fun getAllUsers(): ResponseEntity<List<User>> =
        ResponseEntity(userService.getAllUsers(), HttpStatus.OK)

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: String,
        @Valid @RequestBody updatedUser: User
    ): ResponseEntity<User> = ResponseEntity(userService.updateUserById(id, updatedUser), HttpStatus.OK)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<Void> {
        userService.deleteUserById(id)
        return ResponseEntity(HttpStatus.OK)
    }
}


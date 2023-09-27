package com.rys.springrysakovbohdan.exceptions

class UserNotFoundException : RuntimeException {
    constructor() : super("User not found")
    constructor(message: String) : super(message)
}

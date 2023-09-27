package com.rys.springrysakovbohdan.exceptions

class UnauthorizedAccessException : RuntimeException {
    constructor() : super("Unauthorized access")
    constructor(message: String) : super(message)
}

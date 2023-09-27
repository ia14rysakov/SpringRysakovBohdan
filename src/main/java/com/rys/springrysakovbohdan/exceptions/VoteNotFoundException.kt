package com.rys.springrysakovbohdan.exceptions

class VoteNotFoundException : RuntimeException {
    constructor() : super("Vote not found")
    constructor(message: String) : super(message)
}

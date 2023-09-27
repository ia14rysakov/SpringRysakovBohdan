package com.rys.springrysakovbohdan.exceptions

class PetitionNotFoundException : RuntimeException{
    constructor() : super("Petition not found")
    constructor(message: String) : super(message)
}

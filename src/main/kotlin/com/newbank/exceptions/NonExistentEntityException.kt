package com.newbank.exceptions

class NonExistentEntityException: Exception {
    constructor(): super("Non Existent Entity")

    constructor(message: String): super(message)
}
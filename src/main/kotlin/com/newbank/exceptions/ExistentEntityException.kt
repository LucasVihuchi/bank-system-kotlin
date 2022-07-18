package com.newbank.exceptions

class ExistentEntityException: Exception {
    constructor(): super("Entity Existent")

    constructor(message: String): super(message)
}
package com.newbank.exceptions

class NegativeValueException: Exception {
    constructor(): super("Negative value provided")

    constructor(message: String): super(message)
}
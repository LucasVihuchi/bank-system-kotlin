package com.newbank.exceptions

class IncorrectInfoException : Exception {
    constructor() : super("Incorrect info provided")
    constructor(message: String) : super(message)
}
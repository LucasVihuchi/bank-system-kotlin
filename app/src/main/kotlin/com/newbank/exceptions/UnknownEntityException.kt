package com.newbank.exceptions

class UnknownEntityException : Exception {
    constructor () : super("Unknown entity")

    constructor (message: String) : super(message)
}
package com.newbank.exceptions

class InvalidOperationException : Exception {
    constructor () : super("Invalid operation provided")

    constructor (message: String) : super(message)
}
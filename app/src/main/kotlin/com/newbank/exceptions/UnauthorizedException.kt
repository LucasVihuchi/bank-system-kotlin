package com.newbank.exceptions

class UnauthorizedException : Exception {
    constructor () : super("User unauthorized")

    constructor (message: String) : super(message)
}
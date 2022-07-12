package com.tantofaz.exceptions

class EnumNotFoundException : Exception {
    constructor () : super("Enum not found")

    constructor (message: String) : super(message)
}
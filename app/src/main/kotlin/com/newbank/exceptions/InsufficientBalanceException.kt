package com.newbank.exceptions

class InsufficientBalanceException: Exception {
    constructor() : super("Insufficient balance on account")

    constructor(message: String) : super(message)
}
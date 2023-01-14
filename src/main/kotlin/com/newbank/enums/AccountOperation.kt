package com.newbank.enums

enum class AccountOperation(val id: Int, val friendlyName: String, val pastParticipleTense: String) {
    WITHDRAW(1, "withdraw", "withdrawn"),
    DEPOSIT(2, "deposit", "deposited"),
    TRANSFER(3, "transfer", "transfered")
}
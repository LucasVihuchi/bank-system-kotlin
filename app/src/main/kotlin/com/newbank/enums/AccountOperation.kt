package com.newbank.enums

import com.newbank.exceptions.EnumNotFoundException

enum class AccountOperation(val id: Int, val friendlyName: String, val pastParticipleTense: String) {
    WITHDRAW(1, "withdraw", "withdrawn"),
    DEPOSIT(2, "deposit", "deposited"),
    TRANSFER(3, "transfer", "transfered");

    companion object {
        fun getByFriendlyName(name: String): AccountOperation {
            val operation = values().find {
                it.friendlyName == name
            }
            operation?.let {
                return it
            }
            throw EnumNotFoundException()
        }
    }
}
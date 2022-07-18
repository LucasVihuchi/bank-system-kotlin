package com.newbank.enums

import com.newbank.exceptions.EnumNotFoundException

enum class AccountType(val id: Int, val friendlyName: String) {
    CHECKING(1, "Checking"),
    SAVINGS(2, "Savings");

    companion object {
        fun getById(id: Int): AccountType {
            for (accountType in values()) {
                if (accountType.id == id) {
                    return accountType
                }
            }
            throw EnumNotFoundException()
        }

        fun getByFriendlyName(name: String): AccountType {
            for (accountType in values()) {
                if (accountType.friendlyName == name) {
                    return accountType
                }
            }
            throw EnumNotFoundException()
        }
    }
}
package com.tantofaz.enums

import com.tantofaz.exceptions.EnumNotFoundException

enum class AccountType(val id: Int, val friendlyName: String) {
    CORRENTE(1, "Corrente"),
    POUPANCA(2, "Poupança");

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
package com.newbank.enums

import com.newbank.exceptions.EnumNotFoundException

enum class Agency(val id: Int, val friendlyName: String) {
    A0001(1, "A0001"),
    A0002(2, "A0002");

    companion object {
        fun getById(id: Int): Agency {
            for (agency in values()) {
                if (agency.id == id) {
                    return agency
                }
            }
            throw EnumNotFoundException()
        }

        fun getByFriendlyName(name: String): Agency {
            val agency = values().find {
                it.friendlyName == name
            }
            agency?.let {
                return it
            }
            throw EnumNotFoundException()
        }
    }
}
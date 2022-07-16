package com.tantofaz.enums

import com.tantofaz.exceptions.EnumNotFoundException

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
            for (agency in values()) {
                if (agency.friendlyName == name) {
                    return agency
                }
            }
            throw EnumNotFoundException()
        }
    }
}
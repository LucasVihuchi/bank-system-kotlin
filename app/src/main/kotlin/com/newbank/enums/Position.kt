package com.newbank.enums

import com.newbank.exceptions.EnumNotFoundException

enum class Position(val id: Int, val friendlyName: String) {
    MANAGER(1, "Manager"),
    DIRECTOR(2, "Director"),
    PRESIDENT(3, "President");

    companion object {
        fun getById(id: Int): Position {
            for (position in values()) {
                if(position.id == id) {
                    return position
                }
            }
            throw EnumNotFoundException()
        }

        fun getByFriendlyName(name: String): Position {
            for (position in values()) {
                if(position.friendlyName == name) {
                    return position
                }
            }
            throw EnumNotFoundException()
        }

        fun getFriendlyNames(): Array<String> {
            return values().map { it.friendlyName }.toTypedArray()
        }


    }
}
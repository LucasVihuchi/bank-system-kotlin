package com.tantofaz.enums

import com.tantofaz.exceptions.EnumNotFoundException

enum class Agencia(val id: Int, val friendlyName: String) {
    A0001(1, "A0001"),
    A0002(2, "A0002");

    companion object {
        fun getById(id: Int): Agencia {
            for (agencia in values()) {
                if (agencia.id == id) {
                    return agencia
                }
            }
            throw EnumNotFoundException()
        }

        fun getByFriendlyName(name: String): Agencia {
            for (agencia in values()) {
                if (agencia.friendlyName == name) {
                    return agencia
                }
            }
            throw EnumNotFoundException()
        }
    }
}
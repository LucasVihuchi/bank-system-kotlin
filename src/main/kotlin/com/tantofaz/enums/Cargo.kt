package com.tantofaz.enums

import com.tantofaz.exceptions.EnumNotFoundException

enum class Cargo(val id: Int, val friendlyName: String) {
    GERENTE(1, "Gerente"),
    DIRETOR(2, "Diretor"),
    PRESIDENTE(3, "Presidente");

    companion object {
        fun getById(id: Int): Cargo {
            for (cargo in values()) {
                if(cargo.id == id) {
                    return cargo
                }
            }
            throw EnumNotFoundException()
        }

        fun getByFriendlyName(name: String): Cargo {
            for (cargo in values()) {
                if(cargo.friendlyName == name) {
                    return cargo
                }
            }
            throw EnumNotFoundException()
        }
    }
}
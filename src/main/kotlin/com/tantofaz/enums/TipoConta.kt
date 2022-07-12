package com.tantofaz.enums

import com.tantofaz.exceptions.EnumNotFoundException

enum class TipoConta(val id: Int, val friendlyName: String) {
    CORRENTE(1, "Corrente"),
    POUPANCA(2, "Poupança");

    companion object {
        fun getById(id: Int): TipoConta {
            for (tipoConta in values()) {
                if (tipoConta.id == id) {
                    return tipoConta
                }
            }
            throw EnumNotFoundException()
        }

        fun getByFriendlyName(name: String): TipoConta {
            for (tipoConta in values()) {
                if (tipoConta.friendlyName == name) {
                    return tipoConta
                }
            }
            throw EnumNotFoundException()
        }
    }
}
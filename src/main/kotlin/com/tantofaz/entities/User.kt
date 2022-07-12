package com.tantofaz.entities

import com.tantofaz.exceptions.IncorrectInfoException
import com.tantofaz.exceptions.UnauthorizedException

abstract class User(var name: String, cpf: String, private var password: String) {
    val cpf: String = validateCpf(cpf)

    private fun validateCpf(cpf: String): String {
        if (cpf.length != 11) {
            throw IncorrectInfoException()
        }
        return cpf
    }

    fun changePassword(currentPassword: String, newPassword: String) {
        if (this.password != currentPassword) {
            throw UnauthorizedException()
        }
        this.password = newPassword
    }
}
package com.tantofaz.entities

import com.tantofaz.utils.Validators.Companion.validateCpf
import com.tantofaz.exceptions.IncorrectInfoException
import com.tantofaz.exceptions.UnauthorizedException

abstract class User(var name: String, cpf: String, private var password: String) {
    val cpf: String = if (validateCpf(cpf)) cpf else throw IncorrectInfoException()

    fun changePassword(currentPassword: String, newPassword: String) {
        if (this.password != currentPassword) {
            throw UnauthorizedException()
        }
        this.password = newPassword
    }
}
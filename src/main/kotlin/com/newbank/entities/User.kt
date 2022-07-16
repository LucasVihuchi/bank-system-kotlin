package com.newbank.entities

import com.newbank.utils.Validators.Companion.validateCpf
import com.newbank.exceptions.IncorrectInfoException
import com.newbank.exceptions.UnauthorizedException

abstract class User(var name: String, cpf: String, private var password: String) {
    val cpf: String = if (validateCpf(cpf)) cpf else throw IncorrectInfoException()

    fun changePassword(currentPassword: String, newPassword: String) {
        if (this.password != currentPassword) {
            throw UnauthorizedException()
        }
        this.password = newPassword
    }

    fun login(providedPassword: String) {
        if(this.password != providedPassword) {
            throw UnauthorizedException()
        }
    }
}
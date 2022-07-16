package com.tantofaz.utils

import com.tantofaz.exceptions.IncorrectInfoException

class Validators {

    companion object {
        fun validateCpf(cpf: String?): Boolean {
            return (cpf is String && cpf.length == 11)
        }
    }
}
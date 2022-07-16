package com.newbank.utils

import com.newbank.exceptions.IncorrectInfoException

class Validators {

    companion object {
        fun validateCpf(cpf: String?): Boolean {
            return (cpf is String && cpf.length == 11)
        }
    }
}
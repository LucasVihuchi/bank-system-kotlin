package com.newbank.utils

import com.newbank.exceptions.IncorrectInfoException

class Validators {

    companion object {
        fun validateCpf(cpf: String?): Boolean {
            return (cpf is String && cpf.matches("^[0-9]{11}\$".toRegex()))
        }

        fun validateCpf2(cpf: String?): Boolean {
            if (cpf !is String || cpf.length != 11 || cpf is Num) {
                return false
            }

            val digits: Array<Int> = Array(11, { i -> cpf[i].digitToInt() })
            var areCharactersEqual: Boolean = true
            var index = 0
            for (index in 0..10) {
                if (index > 0) {
                    if (digits[index] != digits[index-1]) {
                        areCharactersEqual = false
                    }
                }
            }
            if(areCharactersEqual) {
                return false
            }

            TODO ("Terminar isso")

            return true
        }
    }
}
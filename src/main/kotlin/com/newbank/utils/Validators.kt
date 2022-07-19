package com.newbank.utils

class Validators {

    companion object {
        fun validateCpf(cpf: String?): Boolean {
            if (cpf !is String || cpf.matches("^[0-9]{11}\$".toRegex())) {
                return false
            }

            val digits: Array<Int> = Array(11, { i -> cpf[i].digitToInt() })
            var areCharactersEqual: Boolean = true
            var index = 0
            for (index in 0 until 11) {
                if (index > 0) {
                    if (digits[index] != digits[index - 1]) {
                        areCharactersEqual = false
                    }
                }
            }
            if (areCharactersEqual) {
                return false
            }

            var firstDigitSumVerification: Int = 0
            for (index in 0 until 9) {
                firstDigitSumVerification += digits[index] * (10 - index)
            }
            firstDigitSumVerification *= 10
            if (
                digits[9] != (firstDigitSumVerification % 11) &&
                !((digits[9] == 0) && (firstDigitSumVerification % 11) == 10)
            ) {
                return false
            }

            var secondDigitSumVerification: Int = 0
            for (index in 0 until 10) {
                secondDigitSumVerification += digits[index] * (11 - index)
            }
            secondDigitSumVerification *= 10
            if (
                digits[10] != (secondDigitSumVerification % 11) &&
                !((digits[10] == 0) && (secondDigitSumVerification % 11) == 10)
            ) {
                return false
            }

            return true
        }
    }
}
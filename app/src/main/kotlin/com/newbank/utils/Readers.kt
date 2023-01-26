package com.newbank.utils

import com.newbank.enums.Agency
import com.newbank.utils.ExceptionHandlers.printErrorMessage
import printStandardInputErrorMessage
import java.util.*

object Readers {
    private fun readStringInput(message: String): String {
        while (true) {
            print(message)
            val userInput: String? = readlnOrNull()
            if (userInput is String && userInput != "") {
                return userInput
            }
            else {
                printStandardInputErrorMessage()
            }
        }
    }

    private fun readIntInput(message: String): Int {
        while (true) {
            val userInput: String = readStringInput(message)
            try {
                return userInput.toInt()
            } catch (e: InputMismatchException) {
                printStandardInputErrorMessage()
            } catch (e: NumberFormatException) {
                printStandardInputErrorMessage()
            }
        }
    }

    fun readDoubleInput(message: String): Double {
        while (true) {
            val userInput: String = readStringInput(message)
            try {
                return userInput.toDouble()
            } catch (e: InputMismatchException) {
                printStandardInputErrorMessage()
            } catch (e: NumberFormatException) {
                printStandardInputErrorMessage()
            }
        }
    }

    fun readOptionsInput(vararg optionsArray: String, message: String = "Select one of the options below:", showZero: Boolean = true): Int {
        val messageBuilder: StringBuilder = StringBuilder()
        messageBuilder.append("${message}\n")
        for (index in 0 .. optionsArray.lastIndex) {
            messageBuilder.append("${index+1} - ${optionsArray[index]}\n")
        }
        if (showZero) messageBuilder.append("0 - Exit\nOption: ")

        val minOption: Int = if (showZero) 0 else 1
        while (true) {
            val numberInput: Int = readIntInput(messageBuilder.toString())
            if (numberInput >= minOption && numberInput <= optionsArray.size) {
                return numberInput
            }
            printStandardInputErrorMessage()
        }
    }

    private fun readUserInfo(maxInputTries: Int = 3, fieldName: String, validationFn: (String) -> Boolean): String? {
        var inputTries = 1
        var field: String
        do {
            field = readStringInput("Insert the $fieldName: ")
            if (validationFn(field)) {
                break
            }
            if (inputTries >= maxInputTries) {
                printErrorMessage("Invalid $fieldName provided $maxInputTries times. Returning to previous menu...")
                return null
            }
            printErrorMessage("Invalid $fieldName provided")
            inputTries++
        } while (true)
        return field
    }

    fun readFullName(maxInputTries: Int = 3): String? {
        return readUserInfo(maxInputTries, "full name") {
            Validators.validateFullName(it)
        }
    }

    fun readCpf(maxInputTries: Int = 3): String? {
        return readUserInfo(maxInputTries, "cpf") {
            Validators.validateCpf(it)
        }
    }

    fun readPassword(maxInputTries: Int = 3): String? {
        return readUserInfo(maxInputTries, "password") {
            Validators.validatePassword(it)
        }
    }

    fun readAdminPassword(maxInputTries: Int = 3): String? {
        return readUserInfo(maxInputTries, "admin password") {
            Validators.validatePassword(it)
        }
    }

    fun readAgency(maxInputTries: Int = 3): Agency? {
        // TODO: simplify this function
        val agencyString = readUserInfo(maxInputTries, "agency") {
            Validators.validateAgency(it)
        }
        return agencyString?.let {
            Agency.getByFriendlyName(it)
        }
    }

    fun readSimulationPeriod(maxInputTries: Int = 3): Int? {
        return readUserInfo(maxInputTries, "simulation period in days") {
            Validators.validateSimulationPeriod(it)
        }?.toInt()
    }
}
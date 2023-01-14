package com.newbank.utils

import com.newbank.utils.ExceptionHandlers.printErrorMessage
import printStandardInputErrorMessage
import java.util.*

object Readers {
    fun readStringInput(message: String): String {
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

    fun readIntInput(message: String): Int {
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

    fun readCpf(maxInputTries: Int = 3): String? {
        var cpfInputTries = 1
        var cpf: String
        do {
            cpf = readStringInput("Insert your cpf: ")
            if (Validators.validateCpf(cpf)) {
                break
            }
            if (cpfInputTries >= maxInputTries) {
                printErrorMessage("Invalid cpf provided $maxInputTries times. Returning to previous menu...")
                return null
            }
            printErrorMessage("Invalid cpf provided")
            cpfInputTries++
        } while (true)
        return cpf
    }

    fun readPassword(maxInputTries: Int = 3): String? {
        var passwordInputTries = 1
        var password: String
        do {
            password = Readers.readStringInput("Insert your password: ")
            if (Validators.validatePassword(password)) {
                break
            }
            if (passwordInputTries >= maxInputTries) {
                printErrorMessage("Invalid password provided $maxInputTries times. Returning to previous menu...")
                return null
            }
            printErrorMessage("Invalid password provided")
            passwordInputTries++
        } while (true)
        return password
    }

}
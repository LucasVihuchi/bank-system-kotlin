package com.newbank.utils

object ExceptionHandlers {
    fun printCustomExceptionMessage(e: Exception) {
        e.message?.let {
            printErrorMessage(it)
        }
    }

    fun printErrorMessage(message: String) {
        println("\n${message}\n")
    }

}
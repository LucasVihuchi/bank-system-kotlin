import com.newbank.repositories.CheckingAccountRepositories
import com.newbank.repositories.SavingsAccountRepositories
import com.newbank.repositories.UserRepositories
import java.util.InputMismatchException

fun main() {
    loadRepositories()

    while (true) {
        println("Welcome, User")
        val selectedOption: Int = readOptionsInput("Login", "Account creation")

        handleMainMenu(selectedOption)
    }
}

fun handleMainMenu(option: Int) {
    TODO("Next Step")
}

fun loadRepositories() {
    UserRepositories.usersLoader()
    CheckingAccountRepositories.accountsLoader()
    SavingsAccountRepositories.accountsLoader()
}

fun readStringInput(message: String): String {
    while (true) {
        print(message)
        val userInput: String? = readLine()
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

fun readOptionsInput(vararg optionsArray: String): Int {
    var messageBuilder: StringBuilder = StringBuilder()
    messageBuilder.append("Select one of the options below:\n")
    for (index in 0 .. optionsArray.lastIndex) {
        messageBuilder.append("${index+1} - ${optionsArray[index]}\n")
    }
    messageBuilder.append("0 - Exit\nOption: ")

    while (true) {
        val numberInput: Int = readIntInput(messageBuilder.toString())
        if (numberInput >= 0 && numberInput <= optionsArray.size) {
            return numberInput
        }
        printStandardInputErrorMessage()
    }
}

fun printStandardInputErrorMessage() {
    println("\nIncorrect input provided, please try again\n")
}
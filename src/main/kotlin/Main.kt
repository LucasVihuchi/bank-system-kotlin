import com.newbank.entities.users.User
import com.newbank.exceptions.NonExistentEntityException
import com.newbank.exceptions.UnauthorizedException
import com.newbank.repositories.CheckingAccountRepositories
import com.newbank.repositories.SavingsAccountRepositories
import com.newbank.repositories.UserRepositories
import com.newbank.utils.Validators
import java.util.InputMismatchException
import kotlin.system.exitProcess

fun main() {
    loadRepositories()

    while (true) {
        println("Welcome, User")
        val selectedOption: Int = readOptionsInput("Login", "Account creation")

        handleMainMenu(selectedOption)
    }
}

fun handleMainMenu(option: Int) {
    when(option) {
        1 -> {
            val user: User = login() ?: return
            initializeATM(user)
        }
        2 -> {
            registerNewAccount()
        }
        0 -> {
            exitProcess(0)
        }
        else -> {
            printErrorMessage("Invalid value provided. Returning to the beginning...")
        }
    }
}

fun registerNewAccount() {
    TODO("Not yet implemented")
}

fun initializeATM(user: User) {
    TODO("Not yet implemented")
}

fun login(): User? {
    var cpfInputTries: Int = 1
    var cpf: String
    do {
        cpf = readStringInput("Insert your cpf: ")
        if (Validators.validateCpf(cpf)) {
            break
        }
        if (cpfInputTries >= 3) {
            printErrorMessage("Invalid cpf provided 3 times. Returning to previous menu...")
            return null
        }
        printErrorMessage("Invalid cpf provided")
        cpfInputTries++
    } while (true)

    var passwordInputTries: Int = 1
    var password: String
    do {
        password = readStringInput("Insert your password: ")
        if (Validators.validatePassword(password)) {
            break
        }
        if (passwordInputTries >= 3) {
            printErrorMessage("Invalid password provided 3 times. Returning to previous menu...")
            return null
        }
        printErrorMessage("Invalid password provided")
        passwordInputTries++
    } while (true)

    val loggedUser: User
    try {
        loggedUser = UserRepositories.getUser(cpf)
    } catch (e: NonExistentEntityException) {
        e.message?.let {
            printErrorMessage(it)
        }
        return null
    }

    try {
        loggedUser.login(password)
    } catch (e: UnauthorizedException) {
        e.message?.let {
            printErrorMessage(it)
        }
        return null
    }

    return loggedUser
}

fun printErrorMessage(message: String) {
    println("\n${message}\n")
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
    printErrorMessage("Incorrect input provided, please try again")
}
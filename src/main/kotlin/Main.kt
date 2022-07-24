import com.newbank.entities.accounts.Account
import com.newbank.entities.accounts.CheckingAccount
import com.newbank.entities.accounts.SavingsAccount
import com.newbank.entities.users.*
import com.newbank.enums.AccountType
import com.newbank.exceptions.*
import com.newbank.repositories.CheckingAccountRepositories
import com.newbank.repositories.SavingsAccountRepositories
import com.newbank.repositories.UserRepositories
import com.newbank.utils.Readers
import com.newbank.utils.Validators
import kotlin.system.exitProcess

fun main() {
    loadRepositories()

    while (true) {
        println("Welcome, User")
        val selectedOption: Int = Readers.readOptionsInput("Login", "Account creation")

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
            return
        }
    }
}

fun registerNewAccount() {
    TODO("Not yet implemented")
}

fun initializeATM(user: User) {
    while (true) {
        val selectedOption: Int = Readers.readOptionsInput("Checking account", "Savings account", message = "Choose which type of account you want:")
        handleAccountSelectionMenu(selectedOption, user)
    }
}

fun handleAccountSelectionMenu(option: Int, user: User) {
    when(option) {
        1 -> {
            val account: Account? = getAccount(user.cpf, AccountType.CHECKING)
            if (account == null) {
                printErrorMessage("Checking account not found")
                return
            }
            handleInitialMainMenu(account, user)
        }
        2 -> {
            val account: Account? = getAccount(user.cpf, AccountType.SAVINGS)
            if (account == null) {
                printErrorMessage("Savings account not found")
                return
            }
            handleInitialMainMenu(account, user)
        }
        0 -> {
            exitProcess(0)
        }
        else -> {
            printErrorMessage("Invalid value provided. Returning to the beginning...")
            return
        }
    }
}

fun getAccount(cpf: String, accountType: AccountType): Account? {
    return try {
        when(accountType) {
            AccountType.CHECKING -> {
                CheckingAccountRepositories.getAccount(cpf)
            }
            AccountType.SAVINGS -> {
                SavingsAccountRepositories.getAccount(cpf)
            }
        }
    } catch (e: NonExistentEntityException) {
        e.message?.let {
            printErrorMessage(it)
        }
        null
    }
}

fun handleInitialMainMenu(account: Account, user: User) {
    while (true) {
        val selectedOption: Int = readInitialMainMenuOption(account, user)
        if (account is CheckingAccount) {
            generateCheckingAccountMenu(account, user, selectedOption)
        } else if (account is SavingsAccount) {
            generateSavingsAccountMenu(account, user, selectedOption)
        }
    }


}

fun generateCheckingAccountMenu(account: CheckingAccount, user: User, selectedOption: Int) {
    when(selectedOption) {
        1 -> {
            val withdrawValue: Double = Readers.readDoubleInput("Insert the value to be withdrawn:")
            try {
                account.withdraw(withdrawValue)
                println("\nThe value R$ ${String.format("%.2f", withdrawValue)} was withdrawn successfully!")
            } catch (e: InsufficientBalanceException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            } catch (e: NegativeValueException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            }
        }
        2 -> {
            val depositValue: Double = Readers.readDoubleInput("Insert the value to be deposited:")
            try {
                account.deposit(depositValue)
                println("\nThe value R$ ${String.format("%.2f", depositValue)} was deposited successfully!")
            } catch (e: InsufficientBalanceException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            } catch (e: NegativeValueException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            }
        }
        3 -> {
            var recipientCpf: String
            var cpfInputTries: Int = 1
            while (true) {
                recipientCpf = Readers.readStringInput("Insert de recipient's cpf: ")
                if (Validators.validateCpf(recipientCpf)) {
                    break
                }
                if (cpfInputTries >= 3) {
                    printErrorMessage("Invalid cpf provided 3 times. Returning to previous menu...")
                    return
                }
                printErrorMessage("Invalid cpf provided")
                cpfInputTries++
            }

            val accountTypeOption: Int = Readers.readOptionsInput("Checking account", "Savings account", showZero = false)
            val accountType: AccountType
            try {
                accountType = AccountType.getById(accountTypeOption)
            } catch (e: NegativeValueException) {
                e.message?.let {
                    printErrorMessage("Account type not found")
                }
                return
            }

            val transferValue: Double = Readers.readDoubleInput("Insert the value to be transfered:")

            try {
                account.transfer(transferValue, recipientCpf, accountType)
                println("\nThe value R$ ${String.format("%.2f", transferValue)} was transfered to '${recipientCpf}' successfully!")
            } catch (e: InsufficientBalanceException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            } catch (e: NegativeValueException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            }
        }
        4 -> {
            account.printBalance()
        }
        5 -> {
            account.generateTaxationReport()
        }
        6 -> {
            TODO("Life insurance")
        }
        7 -> {
            when(user) {
                is Manager -> {
                    user.generateAccountNumberReport()
                }
                is Director -> {
                    user.generateAccountNumberReport()
                }
                is President -> {
                    user.generateAccountNumberReport()
                }
            }
        }
        8 -> {
            when(user) {
                is Director -> {
                    user.generateBankCustomerReport()
                }
                is President -> {
                    user.generateBankStockReport()
                }
            }
        }
        9 -> {
            when(user) {
                is President -> {
                    user.generateBankStockReport()
                }
            }
        }
        0 -> {
            exitProcess(0)
        }
        else -> {
            printErrorMessage("Invalid value provided. Returning to the beginning...")
            return
        }
    }
}

fun generateSavingsAccountMenu(account: SavingsAccount, user: User, selectedOption: Int) {
    when(selectedOption) {
        1 -> {
            val withdrawValue: Double = Readers.readDoubleInput("Insert the value to be withdrawn:")
            try {
                account.withdraw(withdrawValue)
                println("\nThe value R$ ${String.format("%.2f", withdrawValue)} was withdrawn successfully!")
            } catch (e: InsufficientBalanceException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            } catch (e: NegativeValueException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            }
        }
        2 -> {
            val depositValue: Double = Readers.readDoubleInput("Insert the value to be deposited:")
            try {
                account.deposit(depositValue)
                println("\nThe value R$ ${String.format("%.2f", depositValue)} was deposited successfully!")
            } catch (e: InsufficientBalanceException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            } catch (e: NegativeValueException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            }
        }
        3 -> {
            var recipientCpf: String
            var cpfInputTries: Int = 1
            while (true) {
                recipientCpf = Readers.readStringInput("Insert de recipient's cpf: ")
                if (Validators.validateCpf(recipientCpf)) {
                    break
                }
                if (cpfInputTries >= 3) {
                    printErrorMessage("Invalid cpf provided 3 times. Returning to previous menu...")
                    return
                }
                printErrorMessage("Invalid cpf provided")
                cpfInputTries++
            }

            val accountTypeOption: Int = Readers.readOptionsInput("Checking account", "Savings account", showZero = false)
            val accountType: AccountType
            try {
                accountType = AccountType.getById(accountTypeOption)
            } catch (e: NegativeValueException) {
                e.message?.let {
                    printErrorMessage("Account type not found")
                }
                return
            }

            val transferValue: Double = Readers.readDoubleInput("Insert the value to be transfered:")

            try {
                account.transfer(transferValue, recipientCpf, accountType)
                println("\nThe value R$ ${String.format("%.2f", transferValue)} was transfered to '${recipientCpf}' successfully!")
            } catch (e: InsufficientBalanceException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            } catch (e: NegativeValueException) {
                e.message?.let {
                    printErrorMessage(it)
                }
                return
            }
        }
        4 -> {
            account.printBalance()
        }
        5 -> {
            account.generateIncomeSimulation()
        }
        6 -> {
            when(user) {
                is Manager -> {
                    user.generateAccountNumberReport()
                }
                is Director -> {
                    user.generateAccountNumberReport()
                }
                is President -> {
                    user.generateAccountNumberReport()
                }
            }
        }
        7 -> {
            when(user) {
                is Director -> {
                    user.generateAccountNumberReport()
                }
                is President -> {
                    user.generateAccountNumberReport()
                }
            }
        }
        8 -> {
            when(user) {
                is President -> {
                    user.generateAccountNumberReport()
                }
            }
        }
        0 -> {
            exitProcess(0)
        }
        else -> {
            printErrorMessage("Invalid value provided. Returning to the beginning...")
            return
        }
    }
}

fun login(): User? {
    var cpfInputTries: Int = 1
    var cpf: String
    do {
        cpf = Readers.readStringInput("Insert your cpf: ")
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
        password = Readers.readStringInput("Insert your password: ")
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

fun loadRepositories() {
    UserRepositories.usersLoader()
    CheckingAccountRepositories.accountsLoader()
    SavingsAccountRepositories.accountsLoader()
}

fun printErrorMessage(message: String) {
    println("\n${message}\n")
}

fun printStandardInputErrorMessage() {
    printErrorMessage("Incorrect input provided, please try again")
}

fun readInitialMainMenuOption(account: Account, user: User): Int {
    val options = mutableListOf<String>()
    if (account is CheckingAccount) {
        options.addAll(listOf("Withdraw", "Deposit", "Transfer", "Show balance", "Generate tributation report", "Hire life insurance"))
    } else if (account is SavingsAccount) {
        options.addAll(listOf("Withdraw", "Deposit", "Transfer", "Show balance", "Generate income simulation"))
    }
    if (user is Manager || user is Director || user is President) {
        options.add("Generate accounts quantity report on agency(ies)")
    }
    if (user is Director || user is President) {
        options.add("Generate bank customers info report")
    }
    if (user is President) {
        options.add("Generate bank stock report")
    }
    return Readers.readOptionsInput(*options.map{it}.toTypedArray())
}
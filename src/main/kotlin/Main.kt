import com.newbank.entities.accounts.Account
import com.newbank.entities.accounts.CheckingAccount
import com.newbank.entities.accounts.SavingsAccount
import com.newbank.entities.users.Director
import com.newbank.entities.users.Manager
import com.newbank.entities.users.President
import com.newbank.entities.users.User
import com.newbank.enums.AccountOperation
import com.newbank.enums.AccountType
import com.newbank.exceptions.InsufficientBalanceException
import com.newbank.exceptions.NegativeValueException
import com.newbank.exceptions.NonExistentEntityException
import com.newbank.exceptions.UnauthorizedException
import com.newbank.interfaces.DirectorOperations
import com.newbank.interfaces.ManagerOperations
import com.newbank.repositories.CheckingAccountRepositories
import com.newbank.repositories.SavingsAccountRepositories
import com.newbank.repositories.UserRepositories
import com.newbank.utils.ExceptionHandlers.printCustomExceptionMessage
import com.newbank.utils.ExceptionHandlers.printErrorMessage
import com.newbank.utils.Readers
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
    while (true) {
        val selectedOption: Int = Readers.readOptionsInput(*AccountType.getFriendlyNames(), "Choose which type of account you want:")
        handleAccountCreation(selectedOption)
    }
}

fun handleAccountCreation(selectedOption: Int) {
    if (selectedOption < 0 || selectedOption > 2) {
        printErrorMessage("Invalid value provided. Returning to the beginning...")
        return
    }
    if (selectedOption == 0) {
        exitProcess(0)
    }

    val cpf = Readers.readCpf(3)
    val agency = Readers.readAgency(3)


    cpf?.let {
        try {
            val user = UserRepositories.getUser(cpf)
        } catch (e: NonExistentEntityException) {
            handleUserCreation(cpf)
        }
    }
}

fun handleUserCreation(cpf: String) {
    TODO("Not yet implemented")
}

fun initializeATM(user: User) {
    while (true) {
        val selectedOption: Int = Readers.readOptionsInput(*AccountType.getFriendlyNames(), "Choose which type of account you want:")
        handleAccountSelectionMenu(selectedOption, user)
    }
}

fun handleAccountSelectionMenu(option: Int, user: User) {
    val accountType: AccountType = when(option) {
        1 -> {
            AccountType.CHECKING
        }
        2 -> {
            AccountType.CHECKING
        }
        0 -> {
            exitProcess(0)
        }
        else -> {
            printErrorMessage("Invalid value provided. Returning to the beginning...")
            return
        }
    }
    val account: Account? = getAccount(user.cpf, accountType)
    account?.also {
        handleInitialMainMenu(account, user)
    } ?: run {
        printErrorMessage("${accountType.friendlyName} account not found")
        return
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

fun handleOperation(operation: AccountOperation, account: Account, recipientAccountType: AccountType = AccountType.CHECKING, recipientCpf: String = "") {
    val handledValue: Double = Readers.readDoubleInput("Insert the value to be ${operation.pastParticipleTense}:")
    try {
        when (operation) {
            AccountOperation.WITHDRAW -> {
                account.withdraw(handledValue)
            }
            AccountOperation.DEPOSIT -> {
                account.deposit(handledValue)
            }
            AccountOperation.TRANSFER -> {
                account.transfer(handledValue, recipientCpf, recipientAccountType)
            }
        }
        println("\nThe value R$ ${String.format("%.2f", handledValue)} was ${operation.pastParticipleTense} successfully!\n")
    } catch (e: InsufficientBalanceException) {
        printCustomExceptionMessage(e)
    } catch (e: NegativeValueException) {
        printCustomExceptionMessage(e)
    }
}

fun generateCheckingAccountMenu(account: CheckingAccount, user: User, selectedOption: Int) {
    when(selectedOption) {
        1 -> {
            handleOperation(AccountOperation.WITHDRAW, account)
        }
        2 -> {
            handleOperation(AccountOperation.DEPOSIT, account)
        }
        3 -> {
            val recipientCpf: String = Readers.readCpf() ?: return

            val accountTypeOption: Int = Readers.readOptionsInput(*AccountType.getFriendlyNames(), showZero = false)
            val accountType: AccountType
            try {
                accountType = AccountType.getById(accountTypeOption)
            } catch (e: NegativeValueException) {
                e.message?.let {
                    printErrorMessage("Account type not found")
                }
                return
            }

            handleOperation(AccountOperation.TRANSFER, account, accountType, recipientCpf)
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
            if (user is ManagerOperations) {
                user.generateAccountNumberReport()
            }
        }
        8 -> {
            if (user is DirectorOperations) {
                user.generateBankCustomerReport()
            }
        }
        9 -> {
            if (user is President) {
                user.generateBankStockReport()
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
            handleOperation(AccountOperation.WITHDRAW, account)
        }
        2 -> {
            handleOperation(AccountOperation.DEPOSIT, account)
        }
        3 -> {
            val recipientCpf: String = Readers.readCpf() ?: return

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

            handleOperation(AccountOperation.TRANSFER, account, accountType, recipientCpf)
        }
        4 -> {
            account.printBalance()
        }
        5 -> {
            account.generateIncomeSimulation()
        }
        6 -> {
            if (user is ManagerOperations) {
                user.generateAccountNumberReport()
            }
        }
        7 -> {
            if (user is DirectorOperations) {
                user.generateBankCustomerReport()
            }
        }
        8 -> {
            if (user is President) {
                user.generateBankStockReport()
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
    val cpf: String = Readers.readCpf() ?: return null

    val password: String = Readers.readPassword() ?: return null

    val loggedUser: User
    try {
        loggedUser = UserRepositories.getUser(cpf)
    } catch (e: NonExistentEntityException) {
        printCustomExceptionMessage(e)
        return null
    }

    try {
        loggedUser.login(password)
    } catch (e: UnauthorizedException) {
        printCustomExceptionMessage(e)
        return null
    }

    return loggedUser
}

fun loadRepositories() {
    UserRepositories.usersLoader()
    CheckingAccountRepositories.accountsLoader()
    SavingsAccountRepositories.accountsLoader()
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
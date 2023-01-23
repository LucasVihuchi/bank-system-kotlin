package com.newbank.clients

import com.newbank.entities.accounts.CheckingAccount
import com.newbank.entities.accounts.SavingsAccount
import com.newbank.entities.users.*
import com.newbank.enums.Agency
import com.newbank.exceptions.UnknownEntityException
import com.newbank.mocks.EntitiesMocks
import com.newbank.repositories.CheckingAccountRepositories
import com.newbank.repositories.SavingsAccountRepositories
import com.newbank.repositories.UserRepositories
import com.newbank.utils.ExceptionHandlers
import java.io.*
import kotlin.system.exitProcess

const val DATABASE_FOLDER = "databases"

object FilesClient {
    private fun printInternalMessage(message: String) {
        println("\n$message")
    }

    private fun generateUsersSampleData() {
        val users = File(DATABASE_FOLDER + File.separator + "users.txt")
        val writer = BufferedWriter(FileWriter(users, true))
        try {
            writer.use { w ->
                EntitiesMocks.usersMock.forEach {
                    val baseInfo = "${it.name}¨¨${it.cpf}¨¨${it.password}"
                    val (userType, agency) = when (it) {
                        is Customer -> {
                            Pair("c", null)
                        }

                        is Manager -> {
                            Pair("m", it.agency)
                        }

                        is Director -> {
                            Pair("d", null)
                        }

                        is President -> {
                            Pair("p", null)
                        }

                        else -> {
                            ExceptionHandlers.printErrorMessage("Unknown user type")
                            exitProcess(1)
                        }
                    }
                    w.write("$userType¨¨$baseInfo¨¨${agency ?: ""}")
                    w.newLine()
                }
            }
        } catch (e: Exception) {
            ExceptionHandlers.printErrorMessage("Error while setting up files")
        }
    }

    private fun generateCheckingAccountSampleData() {
        val checkingAccounts = File(DATABASE_FOLDER + File.separator + "checkingAccounts.txt")
        val writer = BufferedWriter(FileWriter(checkingAccounts, true))
        try {
            writer.use { w ->
                EntitiesMocks.checkingMock.forEach {
                    w.write("${it.cpfOwner}¨¨${it.agency.friendlyName}¨¨${it.balance}")
                    w.newLine()
                }
            }
        } catch (e: Exception) {
            ExceptionHandlers.printErrorMessage("Error while setting up files")
        }
    }

    private fun generateSavingsAccountSampleData() {
        val savingsAccounts = File(DATABASE_FOLDER + File.separator + "savingsAccounts.txt")
        val writer = BufferedWriter(FileWriter(savingsAccounts, true))
        try {
            writer.use { w ->
                EntitiesMocks.savingsMock.forEach {
                    w.write("${it.cpfOwner}¨¨${it.agency.friendlyName}¨¨${it.balance}¨¨${it.accountBirth}")
                    w.newLine()
                }
            }
        } catch (e: Exception) {
            ExceptionHandlers.printErrorMessage("Error while setting up files")
        }
    }

    private fun createNewFile(file: File, sampleDataGenerator: (() -> Unit)? = null) {
        if (file.createNewFile()) {
            printInternalMessage("${file.nameWithoutExtension} file created sucessfully!")
            sampleDataGenerator?.invoke()
        }
    }

    private fun loadUsers() {
        val users = File(DATABASE_FOLDER + File.separator + "users.txt")
        val reader = BufferedReader(FileReader(users))
        try {
            reader.lines().forEach {
                val usersInfo: List<String> = it.split("¨¨")
                val name = usersInfo[1]
                val cpf = usersInfo[2]
                val password = usersInfo[3]
                val agencyFriendlyName = usersInfo[4]
                val user: User = when (usersInfo[0]) {
                    "c" -> {
                        Customer(name, cpf, password)
                    }

                    "m" -> {
                        Manager(name, cpf, password, Agency.getByFriendlyName(agencyFriendlyName))
                    }

                    "d" -> {
                        Director(name, cpf, password)
                    }

                    "p" -> {
                        President(name, cpf, password)
                    }

                    else -> {
                        throw UnknownEntityException()
                    }
                }
                UserRepositories.addUser(user)
            }
        } catch (e: Exception) {
            ExceptionHandlers.printErrorMessage("Error while reading users file")
            exitProcess(1)
        }
    }

    private fun loadCheckingAccounts() {
        val checkingAccounts = File(DATABASE_FOLDER + File.separator + "checkingAccounts.txt")
        val reader = BufferedReader(FileReader(checkingAccounts))
        try {
            reader.lines().forEach {
                val checkingAccountsInfo: List<String> = it.split("¨¨")
                val cpf = checkingAccountsInfo[0]
                val agencyFriendlyName = checkingAccountsInfo[1]
                val balance = checkingAccountsInfo[2].toDouble()
                CheckingAccountRepositories.addAccount(
                    CheckingAccount(
                        cpf,
                        Agency.getByFriendlyName(agencyFriendlyName),
                        balance
                    )
                )
            }
        } catch (e: Exception) {
            ExceptionHandlers.printErrorMessage("Error while reading checking account file")
            exitProcess(1)
        }
    }

    private fun loadSavingsAccounts() {
        val savingsAccounts = File(DATABASE_FOLDER + File.separator + "savingsAccounts.txt")
        val reader = BufferedReader(FileReader(savingsAccounts))
        try {
            reader.lines().forEach {
                val savingsAccountsInfo: List<String> = it.split("¨¨")
                val cpf = savingsAccountsInfo[0]
                val agencyFriendlyName = savingsAccountsInfo[1]
                val balance = savingsAccountsInfo[2].toDouble()
                val accountBirth = savingsAccountsInfo[3].toInt()
                SavingsAccountRepositories.addAccount(
                    SavingsAccount(
                        cpf,
                        Agency.getByFriendlyName(agencyFriendlyName),
                        accountBirth,
                        balance
                    )
                )
            }
        } catch (e: Exception) {
            ExceptionHandlers.printErrorMessage("Error while reading checking account file")
            exitProcess(1)
        }
    }

    fun loadRepositories() {
        loadUsers()
        loadCheckingAccounts()
        loadSavingsAccounts()
    }

    fun initializeProject(shouldGenerateSampleData: Boolean = false) {
        val dbFolder = File(DATABASE_FOLDER)
        val users = File(DATABASE_FOLDER + File.separator + "users.txt")
        val checkingAccounts = File(DATABASE_FOLDER + File.separator + "checkingAccounts.txt")
        val savingsAccounts = File(DATABASE_FOLDER + File.separator + "savingsAccounts.txt")

        try {
            if (dbFolder.mkdirs()) {
                printInternalMessage("Folders created sucessfully!")
            }
            createNewFile(users) {
                if (shouldGenerateSampleData) generateUsersSampleData()
            }
            createNewFile(checkingAccounts) {
                if (shouldGenerateSampleData) generateCheckingAccountSampleData()
            }
            createNewFile(savingsAccounts) {
                if (shouldGenerateSampleData) generateSavingsAccountSampleData()
            }
        } catch (e: Exception) {
            ExceptionHandlers.printErrorMessage("Error while setting up files")
        }
    }
}
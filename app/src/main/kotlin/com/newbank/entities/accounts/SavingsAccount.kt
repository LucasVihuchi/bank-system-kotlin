package com.newbank.entities.accounts

import com.newbank.clients.DATABASE_FOLDER
import com.newbank.enums.AccountType
import com.newbank.enums.Agency
import com.newbank.interfaces.AccountAttributes
import com.newbank.interfaces.AccountTaxes
import com.newbank.repositories.UserRepositories
import com.newbank.utils.ExceptionHandlers
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.pow

class SavingsAccount(cpfOwner: String, agency: Agency, accountBirth: Int = -1, balance: Double = 0.0) :
    Account(cpfOwner, agency, balance) {

    var accountBirth: Int = accountBirth
        internal set

    override fun deposit(amount: Double) {
        super.deposit(amount)
        if (this.accountBirth < 1 || this.accountBirth > 31) {
            this.accountBirth = LocalDate.now().dayOfMonth
        }
    }

    fun generateIncomeSimulation(value: Double, periodInDays: Int) {
        if (value < 0 || periodInDays < 30) {
            ExceptionHandlers.printErrorMessage("Invalid values provided. Returning to previous menu...")
            return
        }
        val periodInMonths = periodInDays / 30
        val finalValue = value * (1 + AccountTaxes.INTEREST).pow(periodInMonths.toDouble())
        println(
            "The yield on the \$ ${"%.2f".format(value)} deposit for $periodInDays days($periodInMonths meses) was \$ ${
                "%.2f".format(
                    finalValue
                )
            }.\n"
        )
        val currentTime: LocalDateTime = LocalDateTime.now()

        val simulationFile =
            File(DATABASE_FOLDER + File.separator + "sim" + cpfOwner + " " + currentTime.hashCode() + ".txt")
        val writer = BufferedWriter(FileWriter(simulationFile, true))

        writer.use { w ->
            try {
                w.write("Savings accounts yield simulation report - $currentTime")
                w.newLine()
                w.write("Name: " + UserRepositories.getUser(this.cpfOwner).name + " / CPF: " + this.cpfOwner)
                w.newLine()
                w.newLine()
                w.write(
                    "The yield on the \$ ${"%.2f".format(value)} deposit for $periodInDays days($periodInMonths meses) was \$ ${
                        "%.2f".format(
                            finalValue
                        )
                    }.\n"
                )
            } catch (e: Exception) {
                ExceptionHandlers.printErrorMessage("Error while saving report")
            }
        }
    }

    companion object : AccountAttributes {
        override val accountType: AccountType = AccountType.SAVINGS
    }
}
package com.newbank.entities.accounts

import com.newbank.clients.DATABASE_FOLDER
import com.newbank.clients.FilesClient
import com.newbank.enums.AccountOperation
import com.newbank.enums.AccountType
import com.newbank.enums.Agency
import com.newbank.interfaces.AccountAttributes
import com.newbank.interfaces.AccountTaxes
import com.newbank.repositories.UserRepositories
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime


class CheckingAccount(cpfOwner: String, agency: Agency, balance: Double = 0.0) : Account(cpfOwner, agency, balance) {

    fun generateTaxationReport() {
        val currentTime: LocalDateTime = LocalDateTime.now()
        val transactionList = FilesClient.getTransactions()
        var withdrawTaxTotal = 0.0
        var depositTaxTotal = 0.0
        var transferTaxTotal = 0.0
        transactionList.forEach {
            if (it.accountCpf == this.cpfOwner) {
                when (it.accountOperation) {
                    AccountOperation.DEPOSIT -> {
                        depositTaxTotal += AccountTaxes.DEPOSIT
                    }

                    AccountOperation.WITHDRAW -> {
                        withdrawTaxTotal += AccountTaxes.WITHDRAW
                    }

                    AccountOperation.TRANSFER -> {
                        transferTaxTotal += AccountTaxes.TRANSFER
                    }
                }
            }
        }

        val reportFile =
            File(DATABASE_FOLDER + File.separator + "trs" + cpfOwner + " " + currentTime.hashCode() + ".txt")
        val writer = BufferedWriter(FileWriter(reportFile, true))
        try {
            writer.use { w ->
                w.write("Checking accounts tribute report - $currentTime")
                w.newLine()
                w.write(
                    "Name: " + UserRepositories.getUser(this.cpfOwner)
                        .name + " / CPF: " + this.cpfOwner
                )
                w.newLine()
                w.newLine()
                w.write(
                    "Amount spent on withdraw taxes: $ " + String.format(
                        "%.2f",
                        withdrawTaxTotal
                    )
                )
                w.newLine()
                w.write(
                    "Amount spent on deposit taxes: $ " + String.format(
                        "%.2f",
                        depositTaxTotal
                    )
                )
                w.newLine()
                w.write(
                    "Amount spent on transfer taxes: $ " + String.format(
                        "%.2f",
                        transferTaxTotal
                    )
                )
                w.newLine()
                w.write("--------//--------")
                w.newLine()

                // TODO: Implement life insurance info

                w.write("Taxes:")
                w.newLine()
                w.write(
                    "Withdraw tax: $ " + String.format(
                        "%.2f",
                        AccountTaxes.WITHDRAW
                    )
                )
                w.newLine()
                w.write(
                    "Deposit tax: $ " + String.format(
                        "%.2f",
                        AccountTaxes.DEPOSIT
                    )
                )
                w.newLine()
                w.write(
                    "Transfer tax: $ " + String.format(
                        "%.2f",
                        AccountTaxes.TRANSFER
                    )
                )
                w.newLine()
            }
        } catch (e: Exception) {
            println("Error while reading file")
        }
    }

    fun hireLifeInsurance() {
        TODO("Implement when repositories are ready")
    }

    companion object : AccountAttributes {
        override val accountType: AccountType = AccountType.CHECKING
    }
}
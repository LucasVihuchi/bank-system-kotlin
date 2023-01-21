package com.newbank.entities.accounts

import com.newbank.enums.AccountType
import com.newbank.enums.Agency
import com.newbank.exceptions.InsufficientBalanceException
import com.newbank.exceptions.NegativeValueException
import com.newbank.interfaces.AccountTaxes
import com.newbank.repositories.CheckingAccountRepositories
import com.newbank.repositories.SavingsAccountRepositories
import java.time.LocalDate

abstract class Account(var cpfOwner: String, val agency: Agency) {
    protected var balance: Double = 0.0

    fun withdraw(amount: Double) {
        if (amount <= 0) {
            throw NegativeValueException()
        }
        if (this.balance <= (amount + AccountTaxes.WITHDRAW)) {
            throw InsufficientBalanceException()
        }
        this.balance -= (amount + AccountTaxes.WITHDRAW)
    }

    open fun deposit(amount: Double) {
        if (amount <= 0) {
            throw NegativeValueException()
        }
        if (amount <= AccountTaxes.DEPOSIT) {
            throw InsufficientBalanceException()
        }
        this.balance += (amount - AccountTaxes.DEPOSIT)
    }

    fun transfer(amount: Double, recipientCpf: String, accountType: AccountType) {
        if (amount <= 0) {
            throw NegativeValueException()
        }
        if (this.balance <= (amount + AccountTaxes.TRANSFER)) {
            throw InsufficientBalanceException()
        }
        val account: Account = when (accountType) {
            AccountType.CHECKING -> {
                CheckingAccountRepositories.getAccount(recipientCpf)
            }
            AccountType.SAVINGS -> {
                SavingsAccountRepositories.getAccount(recipientCpf)
            }
        }
        if ((account is SavingsAccount) && (account.accountBirth == -1)) {
            account.accountBirth = LocalDate.now().dayOfMonth
        }
        this.balance -= (amount + AccountTaxes.TRANSFER)
        account.balance += amount
    }

    fun printBalance() {
        println("\nCurrent balance on account: $ ${String.format("%.2f", this.balance)}\n")
    }

    fun saveTransaction(amount: Double, transactionType: String, destinedCpf: String) {
        TODO("Implement when repositories are finished")
    }
}
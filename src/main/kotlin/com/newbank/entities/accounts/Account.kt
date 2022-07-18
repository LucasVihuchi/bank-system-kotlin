package com.newbank.entities.accounts

import com.newbank.enums.Agency
import com.newbank.exceptions.InsufficientBalanceException
import com.newbank.interfaces.AccountTaxes

abstract class Account(var cpfOwner: String, val agency: Agency) {
    protected var balance: Double = 0.0

    fun withdraw(amount: Double) {
        if (this.balance <= (amount + AccountTaxes.WITHDRAW)) {
            throw InsufficientBalanceException()
        }
        this.balance -= (amount + AccountTaxes.WITHDRAW)
    }

    open fun deposit(amount: Double) {
        if (amount <= AccountTaxes.DEPOSIT) {
            throw InsufficientBalanceException()
        }
        this.balance += (amount - AccountTaxes.DEPOSIT)
    }

    fun transfer(amount: Double) {
        TODO("Implement when repositories are finished")
    }

    fun printBalance() {
        print("Current balance on account: $ ${String.format("%.2f", this.balance)}")
    }

    fun saveTransaction(amount: Double, transactionType: String, destinedCpf: String) {
        TODO("Implement when repositories are finished")
    }
}
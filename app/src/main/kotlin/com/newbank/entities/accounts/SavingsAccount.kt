package com.newbank.entities.accounts

import com.newbank.enums.AccountType
import com.newbank.enums.Agency
import com.newbank.interfaces.AccountAttributes
import java.time.LocalDate

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

    fun generateIncomeSimulation() {
        TODO("Implement when repositories are ready")
    }

    companion object : AccountAttributes {
        override val accountType: AccountType = AccountType.SAVINGS
    }
}
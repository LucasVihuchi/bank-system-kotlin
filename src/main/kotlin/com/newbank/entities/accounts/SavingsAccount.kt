package com.newbank.entities.accounts

import com.newbank.enums.AccountType
import com.newbank.enums.Agency
import com.newbank.interfaces.AccountAttributes
import com.newbank.interfaces.AccountTaxes
import java.time.LocalDate

class SavingsAccount(cpfOwner: String, agency: Agency, accountBirth: Int = -1): Account(cpfOwner, agency) {

    var accountBirth: Int = accountBirth
        internal set

    override fun deposit(amount: Double) {
        super.deposit(amount)
        if (this.accountBirth < 1 || this.accountBirth > 31) {
            this.accountBirth = LocalDate.now().dayOfMonth
        }
    }

    fun render() {
        if (this.accountBirth < 1 || this.accountBirth > 31) {
            return
        }
        val today: LocalDate = LocalDate.now()
        val isAccountBirthDay: Boolean = this.accountBirth == today.dayOfMonth
        val isLastDayOfMonth: Boolean = today.dayOfMonth == today.lengthOfMonth()
        val isBirthdayBiggerThanMonthLength = this.accountBirth > today.lengthOfMonth()

        if (isAccountBirthDay || (isLastDayOfMonth && isBirthdayBiggerThanMonthLength)) {
            this.balance *= (1 + AccountTaxes.INTEREST)
        }
    }

    fun generateIncomeSimulation() {
        TODO("Implement when repositories are ready")
    }

    companion object : AccountAttributes {
        override val accountType: AccountType = AccountType.SAVINGS
    }
}
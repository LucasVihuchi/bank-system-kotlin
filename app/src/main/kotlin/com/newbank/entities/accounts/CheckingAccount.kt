package com.newbank.entities.accounts

import com.newbank.enums.AccountType
import com.newbank.enums.Agency
import com.newbank.interfaces.AccountAttributes

class CheckingAccount(cpfOwner: String, agency: Agency, balance: Double = 0.0) : Account(cpfOwner, agency, balance) {

    fun generateTaxationReport() {
        TODO("Implement when repositories are ready")
    }

    fun hireLifeInsurance() {
        TODO("Implement when repositories are ready")
    }

    companion object : AccountAttributes {
        override val accountType: AccountType = AccountType.CHECKING
    }
}
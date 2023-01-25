package com.newbank.entities.transactions

import com.newbank.enums.AccountOperation
import com.newbank.enums.AccountType

data class Transaction(
    val amount: Double,
    val accountOperation: AccountOperation,
    val accountType: AccountType,
    val accountCpf: String,
    val destinedCpf: String?
) {
}
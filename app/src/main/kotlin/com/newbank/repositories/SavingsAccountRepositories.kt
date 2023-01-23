package com.newbank.repositories

import com.newbank.entities.accounts.SavingsAccount
import com.newbank.exceptions.ExistentEntityException
import com.newbank.exceptions.NonExistentEntityException

object SavingsAccountRepositories {
    private var accountsMap: HashMap<String, SavingsAccount> = HashMap()

    fun isAccountRegistered(cpf: String): Boolean {
        return accountsMap.containsKey(cpf)
    }

    fun addAccount(savingsAccount: SavingsAccount) {
        if(isAccountRegistered(savingsAccount.cpfOwner)) {
            throw ExistentEntityException()
        }
        accountsMap[savingsAccount.cpfOwner] = savingsAccount
    }

    fun getAccount(cpf: String): SavingsAccount {
        if (!isAccountRegistered(cpf)) {
            throw NonExistentEntityException()
        }
        return accountsMap[cpf]!!
    }

    fun getAccounts(): List<SavingsAccount> {
        return ArrayList<SavingsAccount>(accountsMap.values)
    }
}
package com.newbank.repositories

import com.newbank.entities.accounts.CheckingAccount
import com.newbank.exceptions.ExistentEntityException
import com.newbank.exceptions.NonExistentEntityException

object CheckingAccountRepositories {
    private var accountsMap: HashMap<String, CheckingAccount> = HashMap()

    fun isAccountRegistered(cpf: String): Boolean {
        return accountsMap.containsKey(cpf)
    }

    fun addAccount(savingsAccount: CheckingAccount) {
        if(isAccountRegistered(savingsAccount.cpfOwner)) {
            throw ExistentEntityException()
        }
        accountsMap[savingsAccount.cpfOwner] = savingsAccount
    }

    fun getAccount(cpf: String): CheckingAccount {
        if (!isAccountRegistered(cpf)) {
            throw NonExistentEntityException()
        }
        return accountsMap[cpf]!!
    }

    fun getAccount(): List<CheckingAccount> {
        return ArrayList<CheckingAccount>(accountsMap.values)
    }

    fun accountsLoader() {
        TODO("Implement later")
    }
}
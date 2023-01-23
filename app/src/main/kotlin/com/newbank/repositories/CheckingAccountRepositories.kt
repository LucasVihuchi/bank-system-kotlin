package com.newbank.repositories

import com.newbank.entities.accounts.CheckingAccount
import com.newbank.exceptions.ExistentEntityException
import com.newbank.exceptions.NonExistentEntityException

object CheckingAccountRepositories {
    private var accountsMap: HashMap<String, CheckingAccount> = HashMap()

    fun isAccountRegistered(cpf: String): Boolean {
        return accountsMap.containsKey(cpf)
    }

    fun addAccount(checkingAccount: CheckingAccount) {
        if(isAccountRegistered(checkingAccount.cpfOwner)) {
            throw ExistentEntityException()
        }
        accountsMap[checkingAccount.cpfOwner] = checkingAccount
    }

    fun getAccount(cpf: String): CheckingAccount {
        if (!isAccountRegistered(cpf)) {
            throw NonExistentEntityException()
        }
        return accountsMap[cpf]!!
    }

    fun getAccounts(): List<CheckingAccount> {
        return ArrayList<CheckingAccount>(accountsMap.values)
    }
}
package com.newbank.repositories

import com.newbank.entities.accounts.CheckingAccount
import com.newbank.enums.Agency
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
        addAccount(CheckingAccount("93465029089", Agency.A0001))
        addAccount(CheckingAccount("43643733038", Agency.A0002))
        addAccount(CheckingAccount("80930608046", Agency.A0001))
        addAccount(CheckingAccount("12848534036", Agency.A0002))
    }
}
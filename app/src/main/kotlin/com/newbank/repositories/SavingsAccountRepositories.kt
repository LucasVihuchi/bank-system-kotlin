package com.newbank.repositories

import com.newbank.entities.accounts.SavingsAccount
import com.newbank.enums.Agency
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

    fun accountsLoader() {
        addAccount(SavingsAccount("93465029089", Agency.A0001))
        addAccount(SavingsAccount("43643733038", Agency.A0002))
        addAccount(SavingsAccount("80930608046", Agency.A0001))
        addAccount(SavingsAccount("12848534036", Agency.A0002))
    }
}
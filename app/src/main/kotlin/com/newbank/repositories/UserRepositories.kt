package com.newbank.repositories

import com.newbank.entities.users.President
import com.newbank.entities.users.User
import com.newbank.exceptions.ExistentEntityException
import com.newbank.exceptions.NonExistentEntityException

object UserRepositories {
    private var usersMap: HashMap<String, User> = HashMap()

    private fun isUserRegistered(cpf: String): Boolean {
        return usersMap.containsKey(cpf)
    }

    fun isPresidentRegistered(): Boolean {
        return usersMap.any { it.value is President }
    }

    fun addUser(user: User) {
        if(isUserRegistered(user.cpf)) {
            throw ExistentEntityException()
        }
        usersMap[user.cpf] = user
    }

    fun getUser(cpf: String): User {
        return usersMap[cpf] ?: throw NonExistentEntityException()
    }

    fun getUsers(): List<User> {
        return ArrayList<User>(usersMap.values)
    }


}
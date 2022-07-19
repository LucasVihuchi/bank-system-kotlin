package com.newbank.repositories

import com.newbank.entities.users.*
import com.newbank.enums.Agency
import com.newbank.exceptions.ExistentEntityException
import com.newbank.exceptions.NonExistentEntityException

object UserRepositories {
    private var usersMap: HashMap<String, User> = HashMap()

    fun isUserRegistered(cpf: String): Boolean {
        return usersMap.containsKey(cpf)
    }

    fun isPresidentRegistered(cpf: String): Boolean {
        return usersMap.any { it.value is President }
    }

    fun addUser(user: User) {
        if(isUserRegistered(user.cpf)) {
            throw ExistentEntityException()
        }
        usersMap[user.cpf] = user
    }

    fun getUser(cpf: String): User {
        if (!isUserRegistered(cpf)) {
            throw NonExistentEntityException()
        }
        return usersMap[cpf]!!
    }

    fun getUsers(): List<User> {
        return ArrayList<User>(usersMap.values)
    }

    fun usersLoader() {
        addUser(Customer("Ronald MacDonald", "93465029089", "123456"))
        addUser(Manager("Cristiano Ronaldo", "43643733038", "abc-123", Agency.A0001))
        addUser(Director("Pato Donald", "80930608046", "789789"))
        addUser(President("Caçar Pato, caçar coelho", "12848534036", "poipoi"))
    }
}
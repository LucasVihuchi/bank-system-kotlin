package com.newbank.entities.users

import com.newbank.enums.Position
import com.newbank.interfaces.EmployeeAttributes

class Director(name: String, cpf: String, password: String): Employee(name, cpf, password) {

    companion object : EmployeeAttributes {
        override val position: Position = Position.DIRECTOR
    }

    fun generateAccountNumberReport() {
        TODO("Implement this method when repositories are ready")
    }

    fun generateBankCustomerReport() {
        TODO("Implement this method when repositories are ready")
    }

}
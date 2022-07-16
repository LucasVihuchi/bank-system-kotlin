package com.tantofaz.entities

import com.tantofaz.enums.Position
import com.tantofaz.interfaces.EmployeeAttributes

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
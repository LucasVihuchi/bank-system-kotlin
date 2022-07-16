package com.newbank.entities

import com.newbank.enums.Position
import com.newbank.interfaces.EmployeeAttributes

class President(name: String, cpf: String, password: String): Employee(name, cpf, password) {

    companion object : EmployeeAttributes {
        override val position: Position = Position.PRESIDENT
    }

    fun generateAccountNumberReport() {
        TODO("Implement this method when repositories are ready")
    }

    fun generateBankCustomerReport() {
        TODO("Implement this method when repositories are ready")
    }

    fun generateBankStockReport() {
        TODO("Implement this method when repositories are ready")
    }

}
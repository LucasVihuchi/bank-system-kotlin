package com.tantofaz.entities

import com.tantofaz.enums.Position
import com.tantofaz.interfaces.EmployeeAttributes

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
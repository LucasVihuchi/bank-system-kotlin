package com.newbank.entities.users

import com.newbank.enums.Position
import com.newbank.interfaces.DirectorOperations
import com.newbank.interfaces.EmployeeAttributes
import com.newbank.interfaces.ManagerOperations

class President(name: String, cpf: String, password: String): Employee(name, cpf, password), ManagerOperations, DirectorOperations {

    companion object : EmployeeAttributes {
        override val position: Position = Position.PRESIDENT
    }

    override fun generateAccountNumberReport() {
        TODO("Implement this method when repositories are ready")
    }

    override fun generateBankCustomerReport() {
        TODO("Implement this method when repositories are ready")
    }

    fun generateBankStockReport() {
        TODO("Implement this method when repositories are ready")
    }

}
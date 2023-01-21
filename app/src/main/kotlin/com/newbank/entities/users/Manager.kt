package com.newbank.entities.users

import com.newbank.enums.Agency
import com.newbank.enums.Position
import com.newbank.interfaces.EmployeeAttributes
import com.newbank.interfaces.ManagerOperations

class Manager(name: String, cpf: String, password: String, var agency: Agency) :  Employee(name, cpf, password), ManagerOperations{

    companion object : EmployeeAttributes {
        override val position: Position = Position.MANAGER
    }

    override fun generateAccountNumberReport() {
        TODO("Implement this method when repositories are ready")
    }
}
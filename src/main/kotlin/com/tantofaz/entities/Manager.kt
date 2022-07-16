package com.tantofaz.entities

import com.tantofaz.enums.Agency
import com.tantofaz.enums.Position
import com.tantofaz.interfaces.EmployeeAttributes

class Manager(name: String, cpf: String, password: String, var agency: Agency) :  Employee(name, cpf, password){

    companion object : EmployeeAttributes {
        override val position: Position = Position.MANAGER
    }

    fun generateAccountNumberReport() {
        TODO("Implement this method when repositories are ready")
    }
}
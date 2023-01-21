package com.newbank.interfaces

interface AccountTaxes {
    companion object {
        val WITHDRAW: Double = 0.10
        val DEPOSIT: Double = 0.10
        val TRANSFER: Double = 0.20
        val INTEREST: Double = 0.005
        val LIFE_INSURANCE_HIRE = 0.20
    }
}
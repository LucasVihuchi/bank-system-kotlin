package com.newbank.interfaces

interface AccountTaxes {
    companion object {
        const val WITHDRAW: Double = 0.10
        const val DEPOSIT: Double = 0.10
        const val TRANSFER: Double = 0.20
        const val INTEREST: Double = 0.005
        const val LIFE_INSURANCE_HIRE = 0.20
    }
}
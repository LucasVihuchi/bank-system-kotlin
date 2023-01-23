package com.newbank.mocks

import com.newbank.entities.accounts.CheckingAccount
import com.newbank.entities.accounts.SavingsAccount
import com.newbank.entities.users.*
import com.newbank.enums.Agency

object EntitiesMocks {
    val usersMock: List<User> = listOf(
        Customer("Ronald MacDonald", "93465029089", "123456"),
        Manager("Cristiano Ronaldo", "43643733038", "abc-123", Agency.A0001),
        Director("Pato Donald", "80930608046", "789789"),
        President("Caçar Pato, caçar coelho", "12848534036", "poipoi")
    )

    val checkingMock: List<CheckingAccount> = listOf(
        CheckingAccount("93465029089", Agency.A0001),
        CheckingAccount("43643733038", Agency.A0002),
        CheckingAccount("80930608046", Agency.A0001),
        CheckingAccount("12848534036", Agency.A0002)
    )

    val savingsMock: List<SavingsAccount> = listOf(
        SavingsAccount("93465029089", Agency.A0001),
        SavingsAccount("43643733038", Agency.A0002),
        SavingsAccount("80930608046", Agency.A0001),
        SavingsAccount("12848534036", Agency.A0002)
    )
}
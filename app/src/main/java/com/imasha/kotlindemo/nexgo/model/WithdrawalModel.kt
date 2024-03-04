package com.imasha.kotlindemo.nexgo.model

data class WithdrawalModel(
    var title: String?,
    var accNumber: String?,
    var accName: String?,
    var withdrawalAmount: String?,
    var convenienceFee: String?,
    var transactionAmount: String?
)
package com.imasha.kotlindemo.model

data class CardPaymentModel(
    var title: String?,
    var cardNumber: String?,
    var accNumber: String?,
    var accName: String?,
    var amount: String?,
    var convenienceFee: String?,
    var transactionAmount: String?
)
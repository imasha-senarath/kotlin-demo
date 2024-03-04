package com.imasha.kotlindemo.nexgo.model

data class BillPaymentModel(
    var title: String?,
    var from: String?,
    var accNo: String?,
    var accName: String?,
    var utilityTypeName: String?,
    var utilityProviderName: String?,
    var fieldName: String?,
    var fieldValue: String?,
    var amount: String?,
    var convenienceFee: String?,
    var transactionAmount: String?
)
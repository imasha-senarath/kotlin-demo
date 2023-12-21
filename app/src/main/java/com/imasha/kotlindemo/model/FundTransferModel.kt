package com.imasha.kotlindemo.model

data class FundTransferModel(
    var title: String?,
    var accNumber: String?,
    var accName: String?,
    var receiptNo: String?,
    var receiptName: String?,
    var bank: String?,
    var branch: String?,
    var receiptType: String?,
    var remark: String?,
    var amount: String?,
    var convenienceFee: String?,
    var transactionAmount: String?
)
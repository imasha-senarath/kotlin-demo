package com.imasha.kotlindemo.nexgo.model

data class HistoryTransactionModel(
    var status: String?,
    var transactionType: String?,
    var amount: String?,
    var serviceCharge: String?,
    var from: String?,
    var to: String?,
    var traceNo: String?,
    var org_traceNo: String?,
    var date: String?,
)
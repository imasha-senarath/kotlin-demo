package com.imasha.kotlindemo.model

import java.util.ArrayList


data class SummaryModel(
    var title: String?,
    var totalDebit: String?,
    var totalDebitCount: String?,
    var totalCredit: String?,
    var totalCreditCount: String?,
    var totalCashCharge: String?,
    var totalAccCharge: String?,
    var agentStartAmount: String?,
    var agentBalance: String?,
    var transactionList: ArrayList<SummaryTransactionModel>
)
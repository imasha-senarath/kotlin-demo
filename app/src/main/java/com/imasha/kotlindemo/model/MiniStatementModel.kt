package com.imasha.kotlindemo.model

import java.util.ArrayList

data class MiniStatementModel(
    var title: String?,
    var accNumber: String?,
    var accName: String?,
    var transactionList: ArrayList<MiniTransactionModel>
)
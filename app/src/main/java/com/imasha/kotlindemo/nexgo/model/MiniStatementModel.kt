package com.imasha.kotlindemo.nexgo.model

import java.util.ArrayList

data class MiniStatementModel(
    var title: String?,
    var accNumber: String?,
    var accName: String?,
    var transactionList: ArrayList<com.imasha.kotlindemo.nexgo.model.MiniTransactionModel>
)
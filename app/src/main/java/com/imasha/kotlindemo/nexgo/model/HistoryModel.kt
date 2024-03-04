package com.imasha.kotlindemo.nexgo.model

import java.util.ArrayList

data class HistoryModel(
    var title: String?,
    var transactionList: ArrayList<com.imasha.kotlindemo.nexgo.model.HistoryTransactionModel>
)
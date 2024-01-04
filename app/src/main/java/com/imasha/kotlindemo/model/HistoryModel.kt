package com.imasha.kotlindemo.model

import java.util.ArrayList

data class HistoryModel(
    var title: String?,
    var transactionList: ArrayList<HistoryTransactionModel>
)
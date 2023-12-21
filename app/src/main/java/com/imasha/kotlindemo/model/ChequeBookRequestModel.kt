package com.imasha.kotlindemo.model

data class ChequeBookRequestModel(
    var title: String?,
    var accNumber: String?,
    var accName: String?,
    var quantity: String?,
    var pageCount: String?,
    var collectingBranch: String?,
)
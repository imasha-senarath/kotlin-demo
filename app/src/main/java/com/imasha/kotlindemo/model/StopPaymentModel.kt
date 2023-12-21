package com.imasha.kotlindemo.model

data class StopPaymentModel(
    var title: String?,
    var accNumber: String?,
    var accName: String?,
    var paymentType: String?,
    var refNo: String?,
    var remark: String?,
    var branch: String?,
)
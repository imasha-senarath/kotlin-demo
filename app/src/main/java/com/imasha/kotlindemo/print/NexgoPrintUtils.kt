package com.imasha.kotlindemo.print

import android.app.Activity
import android.graphics.BitmapFactory
import com.imasha.kotlindemo.R
import com.imasha.kotlindemo.model.*
import com.nexgo.oaf.apiv3.device.printer.*


class NexgoPrintUtils (activity: Activity, printer: Printer?, onPrintCompleteTask : OnPrintCompleteTask) {

    var mActivity: Activity? = activity
    var mPrinter: Printer? = printer
    var mOnPrintCompleteTask: OnPrintCompleteTask? = onPrintCompleteTask

    private val FONT_SIZE_SMALL = 20
    private val FONT_SIZE_NORMAL = 24
    private val FONT_SIZE_BIG = 24
    private val fontSmall = FontEntity(DotMatrixFontEnum.CH_SONG_20X20, DotMatrixFontEnum.ASC_SONG_8X16)
    private val fontNormal = FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_12X24)
    private val fontBold = FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_BOLD_16X24)
    private val fontBig = FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_12X24, false, true)

    val Printer_Success = 0
    val Printer_Base_Error = -1000
    val Printer_Print_Fail = Printer_Base_Error - 1
    val Printer_AddPrnStr_Fail = Printer_Base_Error - 2
    val Printer_AddImg_Fail = Printer_Base_Error - 3
    val Printer_Busy = Printer_Base_Error - 4
    val Printer_PaperLack = Printer_Base_Error - 5
    val Printer_Wrong_Package = Printer_Base_Error - 6
    val Printer_Fault = Printer_Base_Error - 7
    val Printer_TooHot = Printer_Base_Error - 8
    val Printer_UnFinished = Printer_Base_Error - 9
    val Printer_NoFontLib = Printer_Base_Error - 10
    val Printer_OutOfMemory = Printer_Base_Error - 11
    val Printer_Other_Error = Printer_Base_Error - 999

    interface OnPrintCompleteTask {
        fun onPrintCompleted(isSuccess: Boolean, msg: String)
    }

    private fun printHeader(headerModel: HeaderModel) {
        mPrinter?.initPrinter()

        val bitmap = BitmapFactory.decodeResource(mActivity?.applicationContext?.resources, R.drawable.ndb2)
        mPrinter?.appendImage(bitmap, AlignEnum.CENTER)

        mPrinter?.appendPrnStr("No. 1, Smart Bank,", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("Colombo 1.", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("Sri Lanka,", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("0111234544", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

        mPrinter?.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

        mPrinter?.appendPrnStr("Date & Time", headerModel.dateTime, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Merchant/Branch ID", headerModel.branchId, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Terminal ID", headerModel.tid, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Batch ID", headerModel.batchId, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Trace Number", headerModel.tranceNo, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Agent", headerModel.agent, FONT_SIZE_NORMAL, false)
    }

    fun printCashBillPayment(headerModel: HeaderModel, billPaymentModel: BillPaymentModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(billPaymentModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("Pay By", billPaymentModel.from, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Utility Type", billPaymentModel.utilityTypeName, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Provider", billPaymentModel.utilityProviderName, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr(billPaymentModel.fieldName, billPaymentModel.fieldValue, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Bill Amount", billPaymentModel.amount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Convenience Fee", billPaymentModel.convenienceFee, FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("Transaction Amount", billPaymentModel.transactionAmount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printAccountBillPayment(headerModel: HeaderModel, billPaymentModel: BillPaymentModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(billPaymentModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("Source A/C No", billPaymentModel.accNo, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Source A/C Name", billPaymentModel.accName, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Utility Type", billPaymentModel.utilityTypeName, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Provider", billPaymentModel.utilityProviderName, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr(billPaymentModel.fieldName, billPaymentModel.fieldValue, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Bill Amount", billPaymentModel.amount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Convenience Fee", billPaymentModel.convenienceFee, FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("Transaction Amount", billPaymentModel.transactionAmount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printBalanceInquiry(headerModel: HeaderModel, balanceInquiryModel: BalanceInquiryModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(balanceInquiryModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("A/C Number", balanceInquiryModel.accNumber, FONT_SIZE_NORMAL, false)
        if(isCusCopy) {
            mPrinter?.appendPrnStr("A/C Product", balanceInquiryModel.nickName, FONT_SIZE_NORMAL, false)
        }
        mPrinter?.appendPrnStr("A/C Name", balanceInquiryModel.accName, FONT_SIZE_NORMAL, false)

        if(isCusCopy) {
            mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
            mPrinter?.appendPrnStr("Available Balance", balanceInquiryModel.availableBalance, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        }

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printCashWithdrawal(headerModel: HeaderModel, withdrawalModel: WithdrawalModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(withdrawalModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("A/C Number", withdrawalModel.accNumber, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("A/C Name", withdrawalModel.accName, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Withdrawal Amount", withdrawalModel.withdrawalAmount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Convenience Fee", withdrawalModel.convenienceFee, FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("Transaction Amount", withdrawalModel.transactionAmount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printStatementRequest(headerModel: HeaderModel, statementModel: StatementModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(statementModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("A/C Number", statementModel.accNumber, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("A/C Name", statementModel.accName, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("From Date", statementModel.fromDate, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("To Date", statementModel.toDate, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Statement Mode", statementModel.statementMode, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr(statementModel.statementMode, statementModel.statementModeValue, FONT_SIZE_NORMAL, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printFundTransfer(headerModel: HeaderModel, fundTransferModel: FundTransferModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(fundTransferModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("Source A/C No", fundTransferModel.accNumber, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Source A/C Name", fundTransferModel.accName, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Receipt A/C No", fundTransferModel.receiptNo, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Receipt Name", fundTransferModel.receiptName, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Bank", fundTransferModel.bank, FONT_SIZE_NORMAL, false)

        //OWN_ACCOUNT or OTHER_BANK
        if (fundTransferModel.receiptType == "OTHER_BANK") {
            mPrinter?.appendPrnStr("Branch", fundTransferModel.branch, FONT_SIZE_NORMAL, false)
        }

        mPrinter?.appendPrnStr("Remark", if (fundTransferModel.remark.equals("")) "-" else fundTransferModel.remark, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Amount", fundTransferModel.amount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Convenience Fee", fundTransferModel.convenienceFee, FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("Transaction Amount", fundTransferModel.transactionAmount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    private fun printBottomTextCus() {
        mPrinter?.appendPrnStr("\n\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("Customer Copy", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("***** Thank You...*****", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("\n\n\n\n\n\n", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

        mPrinter?.startPrint(false) { retCode: Int ->
            mActivity?.runOnUiThread {
                printStatus(retCode)
            }
        }
    }

    private fun printBottomTextAgent() {
        mPrinter?.appendPrnStr("\n\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("Agent Copy", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("***** Thank You...*****", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("\n\n\n\n\n\n", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

        mPrinter?.startPrint(false) { retCode: Int ->
            mActivity?.runOnUiThread {
                printStatus(retCode)
            }
        }
    }

    //TEST
    fun printTest(title: String?) {
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("\n\n\n\n\n\n", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.startPrint(false) { retCode ->
            mActivity?.runOnUiThread {
                printStatus(
                    retCode
                )
            }
        }
    }

    private fun printStatus(code: Int) {
        var errMsg = "Something went wrong"
        when (code) {
            Printer_Success -> {
                mOnPrintCompleteTask?.onPrintCompleted(true, "Printed Successfully")
            }
            Printer_Print_Fail -> {
                errMsg = "Print failed"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            Printer_AddPrnStr_Fail -> {
                errMsg = "Setting string buffer failed"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            Printer_AddImg_Fail -> {
                errMsg = "Setting image buffer failed"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            Printer_Busy -> {
                errMsg = "The printer is busy"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            Printer_PaperLack -> {
                errMsg = "The printer is out of paper"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            Printer_Wrong_Package -> {
                errMsg = "Print packet is wrong"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            Printer_Fault -> {
                errMsg = "Printer failure"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            Printer_TooHot -> {
                errMsg = "The printer is overheating"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            Printer_UnFinished -> {
                errMsg = "The print is not complete"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            Printer_NoFontLib -> {
                errMsg = "The printer does not have a font"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            Printer_OutOfMemory -> {
                errMsg = "The packet is too long"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            Printer_Other_Error -> {
                errMsg = "Other exception error"
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
            else -> {
                mOnPrintCompleteTask?.onPrintCompleted(false, "Error :$errMsg")
            }
        }
    }
}
package com.imasha.kotlindemo.print

import android.app.Activity
import android.graphics.Bitmap
import com.imasha.kotlindemo.model.*
import com.nexgo.oaf.apiv3.device.printer.AlignEnum
import com.nexgo.oaf.apiv3.device.printer.Printer


class NexgoPrintUtils (activity: Activity, printer: Printer?, onPrintCompleteTask : OnPrintCompleteTask) {

    var mActivity: Activity? = activity
    var mPrinter: Printer? = printer
    var mOnPrintCompleteTask: OnPrintCompleteTask? = onPrintCompleteTask

    private val FONT_SIZE_NORMAL = 24

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

    fun printReceipt(receipt: Bitmap?) {
        if(receipt != null) {
            mPrinter?.initPrinter()

            mPrinter?.appendImage(receipt, AlignEnum.CENTER)
            mPrinter?.appendPrnStr("\n\n\n\n\n\n", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

            mPrinter?.startPrint(false) { retCode: Int ->
                mActivity?.runOnUiThread {
                    printStatus(retCode)
                }
            }
        } else {
            printStatus(Printer_AddImg_Fail)
        }
    }

    private fun printHeader(headerModel: HeaderModel) {
        mPrinter?.initPrinter()

        //val bitmap = BitmapFactory.decodeResource(mActivity?.applicationContext?.resources, R.drawable.ndb2)
        //mPrinter?.appendImage(bitmap, AlignEnum.CENTER)

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

    fun printCashWithdrawal(headerModel: HeaderModel, withdrawalModel: WithdrawalModel, isCusCopy: Boolean?) {
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

        if (isCusCopy == true) printBottomTextCus()
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

    fun printDeposit(headerModel: HeaderModel, depositModel: DepositModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(depositModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("A/C Number", depositModel.accNumber, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("A/C Name", depositModel.accName, FONT_SIZE_NORMAL, false)

        if (isCusCopy){
            mPrinter?.appendPrnStr("NIC Number", depositModel.nic, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr("Mobile Number", depositModel.mobile, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr("Remark", depositModel.remark, FONT_SIZE_NORMAL, false)
        }

        mPrinter?.appendPrnStr("Amount", depositModel.amount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Service Charge", depositModel.convenienceFee, FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("Transaction Amount", depositModel.transactionAmount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printSale(headerModel: HeaderModel, saleModel: SaleModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(saleModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (isCusCopy){
            mPrinter?.appendPrnStr("A/C Number", saleModel.accNumber, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr("A/C Name", saleModel.accName, FONT_SIZE_NORMAL, false)
        } else {
            mPrinter?.appendPrnStr("Customer A/C Number", saleModel.accNumber, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr("Customer A/C Name", saleModel.accName, FONT_SIZE_NORMAL, false)
        }

        mPrinter?.appendPrnStr("Sales Outlet", saleModel.salesOutlet, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Remark", saleModel.remark, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Sale Amount", saleModel.amount, FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("Convenience Fee", saleModel.convenienceFee, FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("Transaction Amount", saleModel.transactionAmount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printVoid(headerModel: HeaderModel, voidModel: VoidModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(voidModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("Tran. Type", voidModel.type, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Tran. Date and Time", voidModel.date, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Original Trace ID", voidModel.traceNo, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Transaction Amount", voidModel.transactionAmount, FONT_SIZE_NORMAL, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printStopPayment(headerModel: HeaderModel, stopPaymentModel: StopPaymentModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(stopPaymentModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("A/C Number", stopPaymentModel.accNumber, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("A/C Name", stopPaymentModel.accName, FONT_SIZE_NORMAL, false)

        if(stopPaymentModel.paymentType.equals("Cheque")) {
            mPrinter?.appendPrnStr("Cheque Number", stopPaymentModel.refNo, FONT_SIZE_NORMAL, false)
        } else {
            mPrinter?.appendPrnStr("Card Number", stopPaymentModel.refNo, FONT_SIZE_NORMAL, false)
        }

        mPrinter?.appendPrnStr("Remark", stopPaymentModel.remark, FONT_SIZE_NORMAL, false)

        if(stopPaymentModel.paymentType.equals("Cheque")) {
            mPrinter?.appendPrnStr("Branch", stopPaymentModel.branch, FONT_SIZE_NORMAL, false)
        }

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printLead(headerModel: HeaderModel, leadModel: LeadModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(leadModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("Type", leadModel.type, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Customer Name", leadModel.cusName, FONT_SIZE_NORMAL, false)
        //printLongText("Customer Name", leadModel.cusName, mPrinter)
        mPrinter?.appendPrnStr("NIC No", leadModel.nic, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Branch", leadModel.branch, FONT_SIZE_NORMAL, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printChequeBookRequest(headerModel: HeaderModel, chequeBookRequestModel: ChequeBookRequestModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(chequeBookRequestModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("A/C Number", chequeBookRequestModel.accNumber, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("A/C Name", chequeBookRequestModel.accName, FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("Quantity", chequeBookRequestModel.quantity, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Page Count", chequeBookRequestModel.pageCount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Collecting Branch", chequeBookRequestModel.collectingBranch, FONT_SIZE_NORMAL, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printAccountCardSettlement(headerModel: HeaderModel, cardPaymentModel: CardPaymentModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(cardPaymentModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("Credit Card Number", cardPaymentModel.cardNumber, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("A/C Number", cardPaymentModel.accNumber, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("A/C Name", cardPaymentModel.accName, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Settlement Amount", cardPaymentModel.amount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Convenience Fee", cardPaymentModel.convenienceFee, FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("Transaction Amount", cardPaymentModel.transactionAmount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printCashCardSettlement(headerModel: HeaderModel, cardPaymentModel: CardPaymentModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(cardPaymentModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("Credit Card Number", cardPaymentModel.cardNumber, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Settlement Amount", cardPaymentModel.amount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Convenience Fee", cardPaymentModel.convenienceFee, FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("Transaction Amount", cardPaymentModel.transactionAmount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printMiniStatement(headerModel: HeaderModel, miniStatementModel: MiniStatementModel, isCusCopy: Boolean) {
        printHeader(headerModel)

        var i = 0;

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(miniStatementModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("A/C Number", miniStatementModel.accNumber, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("A/C Name", miniStatementModel.accName, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (isCusCopy) {
            for ((i, bean) in miniStatementModel.transactionList.withIndex()) {
                val numb: String = (i + 1).toString()
                mPrinter?.appendPrnStr(numb + ") " + bean.transName, FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
                mPrinter?.appendPrnStr(bean.transType, bean.amount, FONT_SIZE_NORMAL, false)
                mPrinter?.appendPrnStr(bean.dateTime, bean.balance, FONT_SIZE_NORMAL, false)
                mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
                mPrinter?.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
            }
        }

        if (isCusCopy) printBottomTextCus()
        else printBottomTextAgent()
    }

    fun printTransactionHistory (headerModel: HeaderModel, historyModel: HistoryModel) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(historyModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        for ((i, bean) in historyModel.transactionList.withIndex()) {
            val numb: String = (i + 1).toString()

            if (bean.status.equals("TVOD")) {
                mPrinter?.appendPrnStr(numb + ") " + bean.transactionType + " (VOID)", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
            } else {
                mPrinter?.appendPrnStr(numb + ") " + bean.transactionType, FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
            }

            if (!bean.amount.equals("0")) {
                mPrinter?.appendPrnStr("Amount", bean.amount, FONT_SIZE_NORMAL, false)
            }

            if (!bean.serviceCharge.equals("0")) {
                mPrinter?.appendPrnStr("Convenience Fee", bean.serviceCharge, FONT_SIZE_NORMAL, false)
            }

            if (!bean.from.equals("")) {
                mPrinter?.appendPrnStr("From ", bean.from, FONT_SIZE_NORMAL, false)
            }

            if (!bean.to.equals("")) {
                mPrinter?.appendPrnStr("To", bean.to, FONT_SIZE_NORMAL, false)
            }

            if (!bean.org_traceNo.equals("")) {
                mPrinter?.appendPrnStr("Org.Trace No", bean.org_traceNo, FONT_SIZE_NORMAL, false)
            }

            mPrinter?.appendPrnStr("Tran ID", bean.traceNo, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr("Date & Time", bean.date, FONT_SIZE_NORMAL, false)

            mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
            mPrinter?.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        }

        printBottomTextAgent()
    }

    fun printSummaryReport(headerModel: HeaderModel, summaryModel: SummaryModel) {
        printHeader(headerModel)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(summaryModel.title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        for (bean in summaryModel.transactionList) {
            mPrinter?.appendPrnStr(bean.transName, FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

            if (bean.total.equals("0")) {
                mPrinter?.appendPrnStr("Amount", bean.total, FONT_SIZE_NORMAL, false)
            }

            mPrinter?.appendPrnStr("Count", bean.count, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        }

        mPrinter?.appendPrnStr("Agent Start Amount", summaryModel.agentStartAmount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Total Debit Amount", summaryModel.totalDebit, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Total Debit Count", summaryModel.totalDebitCount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Total Credit Amount", summaryModel.totalCredit, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Total Credit Count", summaryModel.totalCreditCount, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Total Convenience \\n Fee by Cash", summaryModel.totalCashCharge, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Total Convenience \\n Fee from Account", summaryModel.totalAccCharge, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("\n\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("Agent Balance", summaryModel.agentBalance, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        printBottomTextAgent()
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

    private fun printLongText(title: String, value: String?, mPrinter: Printer?) {
        val maxLineLength = 15

        if (value?.length!! <= maxLineLength) {
            mPrinter?.appendPrnStr(title, value, FONT_SIZE_NORMAL, false)
        } else {
            val firstLine = value.substring(0, maxLineLength)
            val secondLine = value.substring(maxLineLength)
            mPrinter?.appendPrnStr(title, firstLine, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr(secondLine, FONT_SIZE_NORMAL, AlignEnum.RIGHT, false)
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
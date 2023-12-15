package com.imasha.kotlindemo

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.RemoteException
import com.nexgo.oaf.apiv3.device.printer.*


class PosPrintUtils (activity: Activity, printer: Printer?, handler: Handler) {

    var mActivity: Activity? = activity
    var mPrinter: Printer? = printer
    var mHandler: Handler? = handler

    private val FONT_SIZE_SMALL = 20
    private val FONT_SIZE_NORMAL = 24
    private val FONT_SIZE_BIG = 24
    private val fontSmall = FontEntity(DotMatrixFontEnum.CH_SONG_20X20, DotMatrixFontEnum.ASC_SONG_8X16)
    private val fontNormal = FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_12X24)
    private val fontBold = FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_BOLD_16X24)
    private val fontBig = FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_12X24, false, true)

    val PRINT_SUCCESS = 0
    val PRINT_FAILED = 1

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
    private val ERROR_PAPERENDED = 240


    /*private fun printHeader(refNo: String?, tranId: String, invoiceNo: String?, isReport: Boolean) {
        mPrinter?.initPrinter()

        val bitmap = BitmapFactory.decodeResource(PosPrintUtils.context.getResources(), R.drawable.ndb2)
        mPrinter?.appendImage(bitmap, AlignEnum.CENTER)

        mPrinter?.appendPrnStr(PosPrintUtils.addressLine1, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr(PosPrintUtils.addressLine2, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr(PosPrintUtils.addressLine3, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr(PosPrintUtils.hotlineNo, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

        mPrinter?.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

        mPrinter?.appendPrnStr(PosPrintUtils.lblDateTime, PosPrintUtils.date, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr(PosPrintUtils.lblAgentId, PosPrintUtils.mID, FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr(PosPrintUtils.lblTerminalId, PosPrintUtils.tID, FONT_SIZE_NORMAL, false
        )
        if (!isReport) {
            mPrinter?.appendPrnStr(PosPrintUtils.lblAgentUserName, PosPrintUtils.mAgentName, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr(PosPrintUtils.lblInvoiceNo, invoiceNo ?: " -- ", FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr(PosPrintUtils.lblFtRefNo, refNo ?: " " + "-- ", FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr(PosPrintUtils.lblTranStatus, "Success", FONT_SIZE_NORMAL, false)
        } else {
            mPrinter?.appendPrnStr(PosPrintUtils.lblBatchId, PosPrintUtils.batchId, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr(PosPrintUtils.lblAgentUserName, PosPrintUtils.mAgentName, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr(PosPrintUtils.lblAgentUserMobile, PosPrintUtils.mAgentMobNo, FONT_SIZE_NORMAL, false)
        }
    }

    fun printCashWithdraw(title: String?, bean: WithdrawalBean, isCusCopy: Boolean) {
        printHeader(bean.getInquiryDataBean().getReferenceNo(), bean.getInquiryDataBean().getTransID(), bean.getInquiryDataBean().getInvoiceNo(), false)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr(PosPrintUtils.accountNumber, bean.getAccountListItem().getAccountNumber(), FONT_SIZE_NORMAL, false)

        val holderName: String = bean.getInquiryDataBean().getAccHolderName()
        val maxLineLength = 15

        if (holderName.length <= maxLineLength) {
            mPrinter?.appendPrnStr(PosPrintUtils.accHolderName, holderName, FONT_SIZE_NORMAL, false)
        } else {
            val firstLine = holderName.substring(0, maxLineLength)
            val secondLine = holderName.substring(maxLineLength)

            mPrinter?.appendPrnStr(PosPrintUtils.accHolderName, firstLine, FONT_SIZE_NORMAL, false)
            mPrinter?.appendPrnStr(secondLine, fontNormal, AlignEnum.RIGHT)
        }

        mPrinter?.appendPrnStr(PosPrintUtils.remark, if (bean.getRemark().isEmpty()) " -- " else bean.getRemark(), FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr(PosPrintUtils.withdrawalAmount, formatterUtil.setFormattedAmount(bean.getAmount()), FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr(PosPrintUtils.serviceCharge, formatterUtil.setFormattedAmount(bean.getInquiryDataBean().getServiceChargeAmount()), FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr(PosPrintUtils.totalAmount, formatterUtil.setFormattedAmount(bean.getTotalAmount()), FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (isCusCopy) printBottomTextCus(true)
        else printBottomTextAgent(true)
    }

    private fun printBottomTextCus(showOtpVerified: Boolean) {
        mPrinter?.appendPrnStr("\n\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        if (showOtpVerified) {
            mPrinter?.appendPrnStr(PosPrintUtils.otpVerified, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        }

        mPrinter?.appendPrnStr(PosPrintUtils.customerCopy, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr(PosPrintUtils.thankYou, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr(PosPrintUtils.furtherDetails1, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr(PosPrintUtils.furtherDetails2, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

        mPrinter?.appendPrnStr("\n\n\n\n\n\n", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

        mPrinter?.startPrint(false,
            OnPrintListener { retCode: Int ->
                mActivity?.runOnUiThread(Runnable {
                    printStatus(
                        retCode
                    )
                })
            })
    }*/

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
                mHandler?.obtainMessage(PRINT_SUCCESS, "Printed Successfully")?.sendToTarget()
            }
            Printer_Print_Fail -> {
                errMsg = "Print failed"
                mHandler?.obtainMessage(PRINT_FAILED, " Error :$errMsg")?.sendToTarget()
            }
            else -> mHandler?.obtainMessage(PRINT_FAILED, " Error :$errMsg")?.sendToTarget()
        }
    }
}
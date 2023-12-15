package com.imasha.kotlindemo

import android.app.Activity
import android.os.Handler
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


    private fun printHeader() {
        mPrinter?.initPrinter()

        //val bitmap = BitmapFactory.decodeResource(PosPrintUtils.context.getResources(), R.drawable.ndb2)
        //mPrinter?.appendImage(bitmap, AlignEnum.CENTER)

        mPrinter?.appendPrnStr("No. 1, Smart Bank,", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("Colombo 1.", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("Sri Lanka,", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("0111234544", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

        mPrinter?.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

        mPrinter?.appendPrnStr("Date & Time", "17/07/2023", FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Merchant/Branch ID", "00000001", FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Terminal ID", "000003", FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Batch ID", "745", FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Trace Number", "898765", FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Agent", "imasha", FONT_SIZE_NORMAL, false)
    }

    fun printCashWithdraw(title: String?, isCusCopy: Boolean) {
        printHeader()

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr(title, FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("A/C Number", "67654345", FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("A/C Name", "Imasha Senarath", FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Withdrawal Amount", "Rs. 1000.00", FONT_SIZE_NORMAL, false)
        mPrinter?.appendPrnStr("Convenience Fee", "Rs. 10.00", FONT_SIZE_NORMAL, false)

        mPrinter?.appendPrnStr("--------------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("Transaction Amount", "Rs. 1010.00", FONT_SIZE_NORMAL, false)
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

        mPrinter?.startPrint(false,
            OnPrintListener { retCode: Int -> mActivity?.runOnUiThread(Runnable {
                    printStatus(retCode)
                })
            })
    }

    private fun printBottomTextAgent() {
        mPrinter?.appendPrnStr("\n\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)

        mPrinter?.appendPrnStr("Agent Copy", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false)
        mPrinter?.appendPrnStr("***** Thank You...*****", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)
        mPrinter?.appendPrnStr("\n\n\n\n\n\n", FONT_SIZE_NORMAL, AlignEnum.CENTER, false)

        mPrinter?.startPrint(false,
            OnPrintListener { retCode: Int -> mActivity?.runOnUiThread(Runnable {
                    printStatus(retCode)
                })
            })
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
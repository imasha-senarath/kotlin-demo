package com.imasha.kotlindemo

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.imasha.kotlindemo.model.*
import com.imasha.kotlindemo.print.NexgoPrintUtils
import com.nexgo.oaf.apiv3.APIProxy
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.device.printer.Printer

class MainActivity : AppCompatActivity() {

    private var deviceEngine: DeviceEngine? = null
    private var printer: Printer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Toast.makeText(this, "S/N: "+getSN(), Toast.LENGTH_SHORT).show()
        //Log.i("NEXGO", "S/N: "+getSN())

        val printBtn: Button = findViewById(R.id.print)
        printBtn.setOnClickListener {
            if(isNexgo()) {
                initPrinter();
                Toast.makeText(this, "Printing...", Toast.LENGTH_SHORT).show()

                val headerModel = HeaderModel("17/07/2023", "000001", "00001", "787", "89876t6", "imasha")
                val billPaymentModel = BillPaymentModel("Utility Bill Payment Account", "Cash", "6756546", "Imasha Senarath", "Telephone", "Mobitel", "Tel No", "077675654", "Rs. 1000", "Rs. 10.00", "Rs. 1010.00")
                val balanceInquiryModel = BalanceInquiryModel("Balance Inquiry", "7876533", "Youth Saving", "Imasha Semarath", "Rs. 1000.00")
                val withdrawalModel = WithdrawalModel("Cash Withdrawal", "7876553", "Imasha Senarath", "Rs. 1000.00", "Rs. 1000.00", "Rs. 10.00")
                val statementModel = StatementModel("Statement Request", "78765", "Imasha Senarath", "01/07/2023", "01/09/2023", "Branch", "Matara")
                val fundTransferModel = FundTransferModel("Other Bank Fund Transfer", "787659", "Imasha Senarath", "76567876", "Imasha", "BOC", "Matara", "OTHER_BANK", "Test", "Rs. 1000.00","10.00","Rs. 1010.00")

                NexgoPrintUtils(this, printer, object: NexgoPrintUtils.OnPrintCompleteTask {
                    override fun onPrintCompleted(isSuccess: Boolean, msg: String) {
                        Log.i("test66", "isSuccess: $isSuccess")
                        Log.i("test66", "msg: $msg")
                    }
                }).printFundTransfer(headerModel, fundTransferModel, true);
            }
        }
    }

    private fun initPrinter(){
        deviceEngine = APIProxy.getDeviceEngine(applicationContext)
        deviceEngine?.getEmvHandler2("app2")
        printer = deviceEngine?.printer
        printer?.setTypeface(Typeface.DEFAULT)
    }

    private fun getSN(): String? {
        val deviceEngine: DeviceEngine = APIProxy.getDeviceEngine(this)
        deviceEngine.getEmvHandler2("app2")
        return deviceEngine.deviceInfo.sn
    }

    private fun isNexgo(): Boolean {
        if (Build.BRAND == "nexgo") return true
        return false
    }
}
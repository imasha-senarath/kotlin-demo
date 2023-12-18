package com.imasha.kotlindemo

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.imasha.kotlindemo.model.HeaderModel
import com.imasha.kotlindemo.model.WithdrawalModel
import com.imasha.kotlindemo.print.NexgoPrintUtils
import com.nexgo.oaf.apiv3.APIProxy
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.device.printer.Printer

class MainActivity : AppCompatActivity() {

    private var deviceEngine: DeviceEngine? = null
    private var printer: Printer? = null

    /*@SuppressLint("HandlerLeak")
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PRINT_FAILED -> {
                    val error = msg.obj as String
                    isPrinted = false
                    Log.i("test66", "Faild")
                }
                PRINT_SUCCESS -> {
                    isPrinted = true
                    Log.i("test66", "Success")
                }
                else -> {}
            }
        }
    }*/

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
                val withdrawalModel = WithdrawalModel("Cash Withdrawal", "78765", "Imasha Semarath", "Rs. 1000.00", "Rs. 1000.00", "Rs. 10.00")

                NexgoPrintUtils(this, printer, object: NexgoPrintUtils.OnPrintCompleteTask {
                    override fun onPrintCompleted(isSuccess: Boolean, msg: String) {
                        Log.i("test66", "isSuccess: $isSuccess")
                        Log.i("test66", "msg: $msg")
                    }
                }).printCashWithdraw(headerModel, withdrawalModel, true);
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
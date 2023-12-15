package com.imasha.kotlindemo

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nexgo.oaf.apiv3.APIProxy
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.device.printer.Printer

class MainActivity : AppCompatActivity() {

    private var deviceEngine: DeviceEngine? = null
    private var printer: Printer? = null

    val PRINT_SUCCESS = 0
    val PRINT_FAILED = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "S/N: "+getSN(), Toast.LENGTH_SHORT).show()

        initPrinter();

        val printBtn: Button = findViewById(R.id.print)
        printBtn.setOnClickListener {
            Toast.makeText(this, "Printing...", Toast.LENGTH_SHORT).show()
            PosPrintUtils(this, printer, handler).printTest("Test")
        }
    }

    private fun initPrinter(){
        deviceEngine = APIProxy.getDeviceEngine(applicationContext)
        deviceEngine?.getEmvHandler2("app2")
        printer = deviceEngine?.printer
        printer?.setTypeface(Typeface.DEFAULT)
    }

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PRINT_FAILED -> Log.i("test66", "Faild")
                PRINT_SUCCESS -> Log.i("test66", "Success")
                else -> {}
            }
        }
    }

    private fun getSN(): String? {
        val deviceEngine: DeviceEngine = APIProxy.getDeviceEngine(this)
        deviceEngine.getEmvHandler2("app2")
        return deviceEngine.deviceInfo.sn
    }

}
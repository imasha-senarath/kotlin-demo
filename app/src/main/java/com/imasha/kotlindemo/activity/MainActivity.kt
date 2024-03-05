package com.imasha.kotlindemo.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.imasha.kotlindemo.R
import com.imasha.kotlindemo.dagger.Car
import com.imasha.kotlindemo.dagger.CarComponent
import com.imasha.kotlindemo.dagger.DaggerCarComponent
import com.imasha.kotlindemo.databinding.ActivityMainBinding
import com.imasha.kotlindemo.nexgo.model.*
import com.imasha.kotlindemo.nexgo.print.NexgoPrintUtils
import com.nexgo.oaf.apiv3.APIProxy
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.device.printer.Printer
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding;

    private var deviceEngine: DeviceEngine? = null
    private var printer: Printer? = null

    @set:Inject var car: Car? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nexgoFunctions();
        daggerFunctions();

    }

    private fun daggerFunctions() {
        val carComponent: CarComponent = DaggerCarComponent.create();
        carComponent.inject(this);
        //car = carComponent.getCar();
        car?.drive();
    }

    private fun nexgoFunctions() {
        //Toast.makeText(this, "S/N: "+getSN(), Toast.LENGTH_SHORT).show()
        //Log.i("NEXGO", "S/N: "+getSN())

        binding.print.setOnClickListener {
            if (isNexgo()) {
                initPrinter();
                Toast.makeText(this, "Printing...", Toast.LENGTH_SHORT).show()

                val headerModel = HeaderModel("17/07/2023", "000001", "00001", "787", "89876t6", "imasha")
                val billPaymentModel = BillPaymentModel("Utility Bill Payment Account", "Cash", "6756546", "Imasha Senarath", "Telephone", "Mobitel", "Tel No", "077675654", "Rs. 1000", "Rs. 10.00", "Rs. 1010.00")
                val balanceInquiryModel = BalanceInquiryModel("Balance Inquiry", "7876533", "Youth Saving", "Imasha Semarath", "Rs. 1000.00")
                val withdrawalModel = WithdrawalModel("Cash Withdrawal", "7876553", "Imasha Senarath", "Rs. 1000.00", "Rs. 1000.00", "Rs. 10.00")
                val statementModel = com.imasha.kotlindemo.nexgo.model.StatementModel("Statement Request", "78765", "Imasha Senarath", "01/07/2023", "01/09/2023", "Branch", "Matara")
                val fundTransferModel = FundTransferModel("Other Bank Fund Transfer", "787659", "Imasha Senarath", "76567876", "Imasha", "BOC", "Matara", "OTHER_BANK", "Test", "Rs. 1000.00", "10.00", "Rs. 1010.00")
                val depositModel = DepositModel("Cash Deposit", "787659", "Imasha Senarath", "970676567V", "077654565", "test", "Rs. 2000.00", "Rs. 20.00", "Rs. 2020.00")
                val saleModel = SaleModel("Sale Receipt", "787659", "Imasha Senarath", "Imasha", "Sale", "Rs. 500.00", "Rs. 10.00", "Rs. 510.00")
                val voidModel = VoidModel("Void Transaction", "Cash Withdrawal", "17/07/2023", "787654", "Rs. 2000.00")
                val stopPaymentModel = StopPaymentModel("Card Stop Payment", "787659", "Imasha Senarath", "Card", "764673", "Test", "Tangalle")
                val leadModel = LeadModel("Lead Request", "Leasing", "Imasha Senarath Imasha Senarath", "879654346V", "Matara")
                val chequeBookRequestModel = ChequeBookRequestModel("Cheque Book Request", "787659", "Imasha Senarath", "3", "25 CR NOOR", "Hikkaduwa")
                val cardPaymentModel = CardPaymentModel("Card Settlement by Cash", "7876775794659", "7864357", "Imasha Senarath", "Rs. 500.00", "Rs. 30.00", "RS. 530.00")

                val miniStatementModel = MiniStatementModel("Mini Statement", "7876775", "Imasha Senarath",
                    arrayListOf<MiniTransactionModel>().apply {
                        add(MiniTransactionModel("100.00", "500.00", "2023-12-22 10:30", "Credit", "Deposit"))
                        add(MiniTransactionModel("50.00", "550.00", "2023-12-22 11:00", "Debit", "Withdrawal"))
                    })

                val historyModel = HistoryModel(
                    "Transaction History",
                    arrayListOf<HistoryTransactionModel>().apply {
                        add(HistoryTransactionModel("TVOD", "Withdrawal", "500.00", "30.00", "6756546", "9756346", "865425", "", "2023-12-22 11:00"))
                        add(HistoryTransactionModel("TVOD", "Deposit", "800.00", "30.00", "3756546", "9756346", "865425", "", "2023-12-22 11:00"))
                    })

                val summaryModel = SummaryModel("Transaction Summary", "500.00", "2", "300.00", "2", "30.00", "400.00", "2000.00", "340.00",
                    arrayListOf<SummaryTransactionModel>().apply {
                        add(SummaryTransactionModel("Withdrawal", "340.00", "4"))
                        add(SummaryTransactionModel("Deposit", "640.00", "2"))
                    })

                NexgoPrintUtils(this, printer, object : NexgoPrintUtils.OnPrintCompleteTask {
                        override fun onPrintCompleted(isSuccess: Boolean, msg: String) {
                            Log.i("test66", "isSuccess: $isSuccess")
                            Log.i("test66", "msg: $msg")
                        }
                    }).printLead(headerModel, leadModel, true);
            }
        }
    }

    private fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            Log.i("test66", "Success base64ToBitmap");
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("test66", "Fail base64ToBitmap: $e")
            null
        }
    }

    private fun initPrinter() {
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
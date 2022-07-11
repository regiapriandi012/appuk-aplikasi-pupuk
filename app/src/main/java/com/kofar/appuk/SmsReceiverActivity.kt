package com.kofar.appuk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sms_receiver.*

class SmsReceiverActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "extra_sms_message"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_receiver)

        title = getString(R.string.incoming_message)
        val senderNo = intent.getStringExtra(EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(EXTRA_SMS_MESSAGE)
        tv_from.text = getString(R.string.coming_from)+": "+senderNo
        tv_message.text = senderMessage
        btn_close.setOnClickListener { finish() }
    }

    override fun onBackPressed() {
    }

}
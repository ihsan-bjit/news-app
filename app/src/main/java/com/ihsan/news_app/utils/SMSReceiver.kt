package com.ihsan.news_app.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast


class SMSReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras
            try {
                if (bundle != null) {
                    val pdus = bundle.get("pdus") as Array<Any>
                    for (i in pdus.indices) {
                        val smsMessage = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                        val sender = smsMessage.displayOriginatingAddress
                        val message = smsMessage.messageBody
                        Toast.makeText(MyApplication.instance, "$message", Toast.LENGTH_SHORT).show()
                        Log.i("SmsReceiver", "sender: $sender; message: $message")

                        // Perform desired action here
                    }
                }
            } catch (e: Exception) {
                Log.e("SmsReceiver", "Exception: $e")
            }
        }
    }
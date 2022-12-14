package com.paya.presentation.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


class MySMSBroadcastReceiver : BroadcastReceiver() {
    private var otpReceiver: OTPReceiveListener? = null

    fun initOTPListener(receiver: OTPReceiveListener) {
        this.otpReceiver = receiver
    }
    override fun onReceive(context: Context?, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val status: Status? = extras!![SmsRetriever.EXTRA_STATUS] as Status?
            if (status != null) {
                when (status.statusCode) {
                    CommonStatusCodes.SUCCESS -> {       // Get SMS message contents
                        // Get SMS message contents
                        var otp: String = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                        Log.d("OTP_Message", otp)
                        // Extract one-time code from the message and complete verification
                        // by sending the code back to your server for SMS authenticity.
                        // But here we are just passing it to MainActivity
                        if (otpReceiver != null) {
                            otp = otp.replace(otp.substring(0,otp.indexOf(":")+2),"").split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                            otpReceiver!!.onOTPReceived(otp)
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                            otpReceiver?.let {it.onOTPTimeOut()}
                    }
                }
            }
        }
    }

    interface OTPReceiveListener {

        fun onOTPReceived(otp: String)

        fun onOTPTimeOut()
    }
}
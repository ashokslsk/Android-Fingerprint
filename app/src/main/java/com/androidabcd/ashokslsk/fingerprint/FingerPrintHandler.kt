package com.androidabcd.ashokslsk.fingerprint

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat.AuthenticationCallback
import androidx.core.os.CancellationSignal
import android.R
import android.R.attr.*
import androidx.core.content.ContextCompat
import android.app.Activity
import android.widget.TextView
import android.widget.Toast


@TargetApi(Build.VERSION_CODES.M)
class FingerPrintHandler (var context: Context): AuthenticationCallback() {

    fun startAuth(fingerPrintManager: FingerprintManagerCompat, cryptObj: FingerprintManagerCompat.CryptoObject){
        val cancellationSignal = CancellationSignal()
        fingerPrintManager.authenticate(cryptObj,0,cancellationSignal,this,null)
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
        super.onAuthenticationError(errMsgId, errString)
        update("There was an Auth Error. " + errString, false)
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        update("Auth Failed. ", false)
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
        super.onAuthenticationHelp(helpMsgId, helpString)
        update("you can now use the app", true)

    }

    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        update("you can now use the app", true)
    }

    public fun update(message: String, value: Boolean) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

        if(!value){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }else {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

    }
}
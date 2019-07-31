package com.androidabcd.ashokslsk.fingerprint

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var fingerprintManagerCompat: FingerprintManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check for : OS version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check for : hardware
            fingerprintManagerCompat = FingerprintManagerCompat.from(this)

            if (!fingerprintManagerCompat?.isHardwareDetected){
                subtitle.text = "Oh! Seems like your device has no fingerprint scanner"
                }
            }
        }
}

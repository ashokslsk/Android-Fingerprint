package com.androidabcd.ashokslsk.fingerprint

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    lateinit var fingerprintManagerCompat: FingerprintManagerCompat
    lateinit var keygaurdManager: KeyguardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check for : OS version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check for : hardware
            fingerprintManagerCompat = FingerprintManagerCompat.from(this)
            keygaurdManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

            if (!fingerprintManagerCompat?.isHardwareDetected){
                subtitle.text = "Oh! Seems like your device has no fingerprint scanner"
            }else if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                subtitle.text = "No permission"
            }else if (!keygaurdManager?.isKeyguardSecure){
                subtitle.text = "Set any lock mechanism in settings"
            }else if(!fingerprintManagerCompat.hasEnrolledFingerprints()){
                subtitle.text = "No fingerprints enrolled, Add atleast one finger print"
            }
        }
    }
}

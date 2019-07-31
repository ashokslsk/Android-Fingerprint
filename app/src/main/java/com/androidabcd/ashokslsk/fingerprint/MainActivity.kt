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
import android.security.keystore.KeyProperties
import android.security.keystore.KeyGenParameterSpec
import android.annotation.TargetApi
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.KeyGenerator
import android.security.keystore.KeyPermanentlyInvalidatedException
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import android.hardware.fingerprint.FingerprintManager




class MainActivity : AppCompatActivity() {

    lateinit var fingerprintManagerCompat: FingerprintManagerCompat
    lateinit var keygaurdManager: KeyguardManager
    lateinit var keyStore: KeyStore
    private val KEY_NAME = "AndroidKey"
    lateinit var cipher: Cipher


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
            }else {
                subtitle.text = "Place your finger on the hardware fingerprint scanner"
                generateKey()

                if (cipherInit()) {
                    val cryptoObject = FingerprintManagerCompat.CryptoObject(cipher)
                    val fingerprintHandler: FingerPrintHandler = FingerPrintHandler(this)
                    fingerprintHandler.startAuth(fingerprintManagerCompat, cryptoObject)

                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun generateKey() {

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore")
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

            keyStore.load(null)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                        KeyProperties.ENCRYPTION_PADDING_PKCS7
                    )
                    .build()
            )
            keyGenerator.generateKey()

        } catch (e: KeyStoreException) {

            e.printStackTrace()

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: NoSuchProviderException) {
            e.printStackTrace()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun cipherInit(): Boolean {
        try {
            cipher =
                Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }


        try {

            keyStore.load(
                null
            )

            val key = keyStore.getKey(KEY_NAME, null) as SecretKey

            cipher.init(Cipher.ENCRYPT_MODE, key)

            return true

        } catch (e: KeyPermanentlyInvalidatedException) {
            return false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }

    }

}

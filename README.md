# Android-Fingerprint

Modern Android mobile phones are equipped with fingerprint sensor for security reasons in this example i will demostrate the comprehensive process to setup and use the fingerprint sensor. 

#### Follow this steps

- Check for the operating system greater then marshmallow. 
- Check for the hardware availability. 
- Check for manifest and runtime permissions. 
- Check for User is using device lock atleast one type of lock mechanism. 
- Check for atleast one fingerprint is being used. 



```kotlin
// check for : OS version
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    // Check for : hardware
    fingerprintManagerCompat = FingerprintManagerCompat.from(this)

    if (!fingerprintManagerCompat?.isHardwareDetected){
        subtitle.text = "Oh! Seems like your device has no fingerprint scanner"
        }
    }
}
```

Other checks are

```kotlin
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

    }
}
```


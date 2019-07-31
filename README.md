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


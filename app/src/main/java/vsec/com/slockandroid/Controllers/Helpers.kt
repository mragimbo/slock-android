package vsec.com.slockandroid.Controllers

import vsec.com.slockandroid.generalModels.PasswordScore
import java.security.MessageDigest

object Helpers {

    fun makeSha512Hash(payload: String, salt: String): String {
        val md = MessageDigest.getInstance("SHA-512")
        val digest = md.digest((payload + salt).toByteArray())
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    fun checkEmailIsValid(email: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkPasswordIsStrong(passwordScore: Int): PasswordScore{
        if(passwordScore in 0..12)
            return PasswordScore.WEAK
        if(passwordScore in 13..20)
            return PasswordScore.AVERAGE
        if(passwordScore in 21..33)
            return PasswordScore.STRONG
        if(passwordScore > 33)
            return PasswordScore.MARVELOUS

        return PasswordScore.WEAK
    }
}
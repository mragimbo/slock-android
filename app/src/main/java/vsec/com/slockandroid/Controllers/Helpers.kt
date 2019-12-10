package vsec.com.slockandroid.Controllers

import java.security.MessageDigest

object Helpers{
    fun makeSha512Hash(payload: String, salt: String): String {
        val md = MessageDigest.getInstance("SHA-512")
        val digest = md.digest((payload + salt).toByteArray())
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
package vsec.com.slockandroid.Controllers

import java.security.MessageDigest

class Helpers{
    companion object {
        fun makeSha512Hash(payload: String, salt: String): String {
            val md = MessageDigest.getInstance("SHA-512")
            val digest = md.digest((payload + salt).toByteArray())
            val sb = StringBuilder()
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }
    }
}
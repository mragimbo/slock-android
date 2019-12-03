package vsec.com.slockandroid.LoginActivity
import vsec.com.slockandroid.LoginActivity.models.User
import java.security.MessageDigest

class LoginPresenter(private val view: View) {

    private val user: User
    //salt for mitegating rainbowtables
    private val salt: String = "ad18cbd8676391bf70b196e6df30065b"

    init {
        this.user = User()
    }

    fun updateEmail(email: String) {
        user.setEmail(email)

    }

    fun updatePassword(password: String) {
        val md = MessageDigest.getInstance("SHA-512")
        val digest = md.digest((password + this.salt).toByteArray())
        val sb = StringBuilder()
        user.setHashedPassword(digest.fold("") { str, it -> str + "%02x".format(it) })

    }

    interface View {

    }
}

package vsec.com.slockandroid.Presenters.LoginActivity
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.LoginActivity.models.User
import java.security.MessageDigest

class LoginPresenter(private val view: View) {

    private val user: User
    //salt for mitegating rainbowtables
    init {
        user = User()
    }

    fun updateEmail(email: String) {
        user.setEmail(email)

    }

    fun updatePassword(password: String) {
        user.setHashedPassword(Helpers.makeSha512Hash(password,user.salt))
    }

    fun sendLoginRequestToApi(){
        //do api call
        user.setHashedPassword("");
    }

    fun getUserPassHash(): String{
        return user.getPassHash();
    }

    interface View {

    }
}

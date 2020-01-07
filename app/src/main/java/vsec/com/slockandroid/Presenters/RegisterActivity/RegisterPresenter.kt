package vsec.com.slockandroid.Presenters.RegisterActivity
import android.app.Activity
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.generalModels.User


class RegisterPresenter(private val view: View) {

    private val user: User
    init {
        user = User()
    }

    fun assertEqual(element1: String, element2: String): Boolean = (element1 == element2)

    fun checkEmailValidity(element: String): Boolean {
        return Helpers.checkEmailIsValid(element)
    }

    fun checkNameField(element: String): Boolean {
        val forbiddenChars: String = "1234567890" +
                """!#¤%&/()=?{}<>|@£${"$"}€+~""" + "\"" + "\\\\"


        if( element.matches( Regex(".*["+forbiddenChars+"].*") ) ) {
            return false
        }
        return true
    }


    //fun checkPasswdValidity(element: String): Boolean {
       // return Helpers.checkPasswordIsStrong(element)
    //}

    interface View {
        fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String>)
    }
}
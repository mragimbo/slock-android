package vsec.com.slockandroid.Presenters.RegisterActivity
import android.app.Activity
import android.os.AsyncTask
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView
import vsec.com.slockandroid.generalModels.User
import java.lang.Class

class RegisterPresenter(private val view: View) : AsyncTask<User, Void, Boolean>(){

    private val user: User
    init {
        user = User()
    }
    fun updateFirstName(fName: String){
        user.setFirstName(fName)
    }
    fun updateLastName(lName: String){
        user.setLastName(lName)
    }
    fun updateEmail(email: String){
        user.setEmail(email)
    }
    fun updatePasswd(passwd: String){
        user.setHashedPassword(Helpers.makeSha512Hash(passwd,user.salt))
    }

    fun assertEqual(element1: String, element2: String): Boolean = (element1 == element2)

    fun checkEmailValidity(element: String): Boolean {
        return Helpers.checkEmailIsValid(element)
    }

    fun checkNameField(element: String): Boolean {
        val forbiddenChars: String = "1234567890" +
                """!#¤%&/()=?{}<>|@£${"$"}€+~""" + "\"" + "\\\\"


        if( element.matches( Regex(".*["+forbiddenChars+"].*") ) ) { return false }
            return true
        }


    fun sendRegisterRequestToApi(): Boolean {
        this.execute(this.user)
        return true
    }

    interface View {
        fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String> = HashMap())
    }

    override fun onPostExecute(result: Boolean?) {
        this.view.changeActivity(LoginView::class.java  as Class<Activity>)
    }

    override fun doInBackground(vararg params: User?): Boolean {
        if(params[0] != null){
            return ApiController.registerUser(params[0] as User)
        }
        return false
    }
}
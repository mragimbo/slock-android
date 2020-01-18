package vsec.com.slockandroid.Presenters.RegisterActivity
import android.os.AsyncTask
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView
import vsec.com.slockandroid.generalModels.User
import java.lang.Class

class RegisterPresenter(private val view: View) {

    private val user: User
    private var task: Task = Task(this.view)

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
        user.setHashedPassword(Helpers.makeSha512Hash(passwd,User.salt))
    }

    fun updateUsername(uname: String){
        user.setUsername(uname)
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


    fun sendRegisterRequestToApi() {
        this.task.execute(this.user)
    }

    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String> = HashMap())
        fun toastLong(message: String)
    }

    companion object{
        class Task(private var view: View) : AsyncTask<User, Void, String>() {

            override fun doInBackground(vararg params: User): String {
                return ApiController.registerUser(params[0])
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                //if user is authenticated or
                when (result) {
                    "200" -> {
                        this.view.toastLong("please check your mail")
                        this.view.changeActivity(LoginView::class.java)
                    }
                    "400" -> {
                        this.view.toastLong("account already exists")
                        this.view.changeActivity(RegisterView::class.java)
                    }else -> {
                        this.view.toastLong("internal error")
                }
                }
            }
        }
    }
}
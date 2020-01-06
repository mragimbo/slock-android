package vsec.com.slockandroid.Presenters.LoginActivity
import android.app.Activity
import android.os.AsyncTask
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.generalModels.User

class LoginPresenter(private val view: View) : AsyncTask<User, Void, String>() {

    private val user: User
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
        this.execute(this.user)
    }

    fun checkEmailValid(email: String): Boolean{
        return Helpers.checkEmailIsValid(email)
    }
    fun getuserObject(): String{
        return user.toJSON()
    }

    interface View {
        fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String> = HashMap())
    }

    override fun onPostExecute(result: String) {
        //local storage safe token
        super.onPostExecute(result)
        this.view.changeActivity(HomeView::class.java  as Class<Activity>)
    }

    override fun doInBackground(vararg params: User): String {
        return ApiController.loginUser(params[0])
    }
}


package vsec.com.slockandroid.Presenters.SettingsActivity

import android.app.Activity
import android.os.AsyncTask
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView
import vsec.com.slockandroid.Presenters.RegisterActivity.RegisterPresenter
import vsec.com.slockandroid.Presenters.RegisterLockActivity.RegisterLockPresenter
import vsec.com.slockandroid.generalModels.Lock
import vsec.com.slockandroid.generalModels.User

class SettingsPresenter(private val view: View) {
    private val user: User
    private lateinit var logoutTask: LogoutTask
    init {
        user = User()
        logoutTask = LogoutTask(view)
    }

    fun updatePasswd(passwd: String){
        user.setHashedPassword(Helpers.makeSha512Hash(passwd,user.salt))
    }

    fun assertEqual(element1: String, element2: String): Boolean = (element1 == element2)
    fun logOutUser() {
        this.logoutTask.execute()
        this.logoutTask = LogoutTask(this.view)
    }


    interface View {
        fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String> = HashMap())
        fun toastLong(message: String)
    }

    inner class LogoutTask(private var view: SettingsPresenter.View) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String? {
            return ApiController.LogoutUser()
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            when (result) {
                "200" -> this.view.changeActivity(LoginView::class.java as Class<Activity>)
                else -> this.view.toastLong("Logout Failed")
            }
        }
    }
}
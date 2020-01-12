package vsec.com.slockandroid.Presenters.SettingsActivity

import android.app.Activity
import android.os.AsyncTask
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView
import vsec.com.slockandroid.Presenters.RegisterActivity.RegisterPresenter
import vsec.com.slockandroid.Presenters.RegisterLockActivity.RegisterLockPresenter
import vsec.com.slockandroid.generalModels.ChangePasswordModel
import vsec.com.slockandroid.generalModels.Lock
import vsec.com.slockandroid.generalModels.User

class SettingsPresenter(private val view: View) {
    private lateinit var changePasswordModel: ChangePasswordModel
    private lateinit var logoutTask: LogoutTask
    init {
        logoutTask = LogoutTask(view)
        changePasswordModel = ChangePasswordModel()
    }

    fun updateOldPassword(passwd: String){
        changePasswordModel.setOldHashedPassword(Helpers.makeSha512Hash(passwd,User.salt))
    }

    fun updateNewPassword(passwd: String){
        changePasswordModel.setNewHashedPassword(Helpers.makeSha512Hash(passwd, User.salt))
    }

    fun assertEqual(element1: String, element2: String): Boolean = (element1 == element2)
    fun logOutUser() {
        this.logoutTask.execute()
        this.logoutTask = LogoutTask(this.view)
    }

    fun sendPasswordUpdateRequest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    inner class ChangeDetailTask(private var view: SettingsPresenter.View) : AsyncTask<ChangePasswordModel, Void, String>() {

        override fun doInBackground(vararg params: ChangePasswordModel): String? {
                return ApiController.ChangeDetails(params[0])
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            when (result) {
                "200" -> this.view.toastLong("Your password has been changed")
                else -> this.view.toastLong("Could not change your password")
            }
        }
    }
}
package vsec.com.slockandroid.Presenters.SettingsActivity

import android.os.AsyncTask
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView
import vsec.com.slockandroid.generalModels.ChangePasswordModel
import vsec.com.slockandroid.generalModels.User

class SettingsPresenter(private val view: View) {
    private var changePasswordModel: ChangePasswordModel
    private var logoutTask: LogoutTask
    private var changeDetailTask: ChangeDetailTask
    init {
        logoutTask = LogoutTask(view)
        changeDetailTask = ChangeDetailTask(this.view)
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
        changeDetailTask = ChangeDetailTask(this.view)
        changeDetailTask.execute(this.changePasswordModel)
    }


    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String> = HashMap())
        fun toastLong(message: String)
    }

    companion object{
        class LogoutTask(private var view: SettingsPresenter.View) : AsyncTask<Void, Void, String>() {

            override fun doInBackground(vararg params: Void?): String? {
                return ApiController.LogoutUser()
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                when (result) {
                    "200" -> this.view.changeActivity(LoginView::class.java)
                    "401" -> {
                        this.view.toastLong("please login again")
                        ApiController.clearSession()
                        this.view.changeActivity(LoginView::class.java)
                    }
                    else -> this.view.toastLong("Logout Failed")

                }
            }
        }

        class ChangeDetailTask(private var view: SettingsPresenter.View) : AsyncTask<ChangePasswordModel, Void, String>() {

            override fun doInBackground(vararg params: ChangePasswordModel): String? {
                return ApiController.ChangeDetails(params[0])
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                when (result) {
                    "200" -> this.view.toastLong("Your password has been changed")
                    "401" -> {
                        this.view.toastLong("please login again")
                        ApiController.clearSession()
                        this.view.changeActivity(LoginView::class.java)
                    }
                    else -> this.view.toastLong("Could not change your password")
                }
        }
    }
    }
}
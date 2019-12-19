package vsec.com.slockandroid.Presenters.SettingsActivity

import android.app.Activity
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.RegisterActivity.RegisterPresenter
import vsec.com.slockandroid.generalModels.User

class SettingsPresenter(private val view: RegisterPresenter.View) {
    private val user: User
    init {
        user = User()
    }

    fun updatePasswd(passwd: String){
        user.setHashedPassword(Helpers.makeSha512Hash(passwd,user.salt))
    }

    fun assertEqual(element1: String, element2: String): Boolean = (element1 == element2)






    interface View {
        fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String> = HashMap())
    }
}
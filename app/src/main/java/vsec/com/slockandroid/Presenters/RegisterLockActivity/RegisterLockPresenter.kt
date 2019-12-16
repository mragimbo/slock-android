package vsec.com.slockandroid.Presenters.RegisterLockActivity

import android.app.Activity
import vsec.com.slockandroid.Presenters.RegisterActivity.RegisterPresenter

class RegisterLockPresenter (private val view: RegisterPresenter.View){
    interface View {
        fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String>)
    }
}
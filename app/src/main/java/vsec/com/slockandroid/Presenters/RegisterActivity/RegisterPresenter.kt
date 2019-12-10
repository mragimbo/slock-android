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

    interface View {
        fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String> = HashMap())
    }
}
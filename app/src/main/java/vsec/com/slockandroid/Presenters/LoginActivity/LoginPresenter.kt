package vsec.com.slockandroid.Presenters.LoginActivity
import android.app.Activity
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.generalModels.User

class LoginPresenter(private val view: View) {

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
        var succeeded: Boolean = ApiController.loginUser(user)
        //do api call
        if(succeeded){
            //change activity
            view.changeActivity(HomeView::class.java as Class<Activity>)
        }
        user.clear()
    }

    fun getuserObject(): String{
        return user.toJSON()
    }

    interface View {
        fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String> = HashMap())
    }
}

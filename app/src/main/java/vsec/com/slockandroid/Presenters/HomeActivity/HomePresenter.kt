package vsec.com.slockandroid.Presenters.HomeActivity

import android.app.Activity

class HomePresenter(private val view: View) {


    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extra: Map<String, String>)
    }

}

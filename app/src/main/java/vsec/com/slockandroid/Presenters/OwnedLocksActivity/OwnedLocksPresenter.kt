package vsec.com.slockandroid.Presenters.OwnedLocksActivity

import android.app.Activity


class OwnedLocksPresenter(private val view: View){
    interface View {
        fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String>)
    }
}
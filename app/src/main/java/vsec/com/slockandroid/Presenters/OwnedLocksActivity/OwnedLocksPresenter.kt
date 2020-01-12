package vsec.com.slockandroid.Presenters.OwnedLocksActivity

import android.app.Activity


class OwnedLocksPresenter(private val view: View){
    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String>)
    }
}
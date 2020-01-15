package vsec.com.slockandroid.Presenters.OwnedLocksActivity

import vsec.com.slockandroid.generalModels.*

class OwnedLocksPresenter(private val view: View){
    fun executeCommand(lock: Lock, command: Int) {

    }

    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String>)
    }
}
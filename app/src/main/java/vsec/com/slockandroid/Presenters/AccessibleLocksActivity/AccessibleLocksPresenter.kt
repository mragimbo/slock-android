package vsec.com.slockandroid.Presenters.AccessibleLocksActivity

class AccessibleLocksPresenter(private val view: View) {

    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String> = HashMap())
        fun toastLong(message: String)
    }
}
package vsec.com.slockandroid.Presenters.HomeActivity

class HomePresenter(private val view: View) {


    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extra: Map<String, String>)
    }

}

package vsec.com.slockandroid.Presenters.RegisterActivity

import android.app.Activity
import android.os.Bundle
import vsec.com.slockandroid.R


class RegisterViewStep2 : Activity(), RegisterPresenter.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_2)

        val extras = intent.extras
        if (extras != null) {
            val firstName = extras.getString("firstName")
            val lastName = extras.getString("lastName")
            val email = extras.getString("email")
        }



    }

    override fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String>) {}
}

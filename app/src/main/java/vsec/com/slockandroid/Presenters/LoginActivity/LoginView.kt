package vsec.com.slockandroid.Presenters.LoginActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.Presenters.RegisterActivity.RegisterView
import vsec.com.slockandroid.R

class LoginView : Activity(), LoginPresenter.View {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        this.presenter = LoginPresenter(this)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener{
            this.presenter.updateEmail(in_email.text.toString())
            this.presenter.updatePassword(in_password.text.toString())
            in_password.text.clear();
            this.presenter.sendLoginRequestToApi()
        }

        btn_register.setOnClickListener{
            this.changeActivity(RegisterView::class.java as Class<Activity>)
        }
    }

    override fun changeActivity(toActivity: Class<Activity>, extras: Map<String, String>) {
        var intent: Intent = Intent(this, toActivity)
        for(extra in extras){
            intent.putExtra(extra.key, extra.value)
        }
        startActivity(intent)
    }

}

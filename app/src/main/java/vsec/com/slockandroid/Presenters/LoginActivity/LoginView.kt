package vsec.com.slockandroid.Presenters.LoginActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.R

class LoginView : Activity(), LoginPresenter.View {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.presenter = LoginPresenter(this)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener{
            this.presenter.updateEmail(in_email.text.toString())
            this.presenter.updatePassword(in_password.text.toString())
            in_password.text.clear();
            this.presenter.sendLoginRequestToApi()

        }
    }

    override fun changeActivity(toActivity: Class<HomeView>, extras: Map<String, String>) {
        var intent: Intent = Intent(this, toActivity)
        for(extra in extras){
            intent.putExtra(extra.key, extra.value)
        }
        startActivity(intent)
    }

}

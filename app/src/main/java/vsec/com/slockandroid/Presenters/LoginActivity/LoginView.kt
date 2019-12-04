package vsec.com.slockandroid.Presenters.LoginActivity

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
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
            tv_userpassword.text = this.presenter.getUserPassHash();

        }
    }
}

package vsec.com.slockandroid.LoginActivity

import android.app.Activity
import android.os.Bundle
import vsec.com.slockandroid.R

class LoginView : Activity(), LoginPresenter.View {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.presenter = LoginPresenter(this)
        setContentView(R.layout.activity_login)
    }
}

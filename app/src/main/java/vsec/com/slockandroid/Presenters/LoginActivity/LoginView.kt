package vsec.com.slockandroid.Presenters.LoginActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.Presenters.RegisterActivity.RegisterView
import vsec.com.slockandroid.R
import vsec.com.slockandroid.generalModels.ButtonState
import java.util.*

class LoginView : Activity(), LoginPresenter.View {

    private lateinit var presenter: LoginPresenter
    private var buttonState: EnumSet<ButtonState> = EnumSet.noneOf(ButtonState::class.java)

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

        in_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(presenter.checkEmailValid(p0.toString())){
                    buttonState.add(ButtonState.EMAIL_VALID)
                }else{
                    buttonState.remove(ButtonState.EMAIL_VALID)
                }
                updateButtonState()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        in_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0 != null && p0.toString() != "") {
                    buttonState.add(ButtonState.PASSWORD_VALID)
                }else{
                    buttonState.remove(ButtonState.PASSWORD_VALID)
                }
                updateButtonState()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    fun updateButtonState(){
        btn_login.isEnabled = buttonState.containsAll(List(2){ButtonState.EMAIL_VALID; ButtonState.PASSWORD_VALID })
    }

    override fun changeActivity(toActivity: Class<Activity>, extras: Map<String, String>) {
        var intent: Intent = Intent(this, toActivity)
        for(extra in extras){
            intent.putExtra(extra.key, extra.value)
        }
        startActivity(intent)
    }

}

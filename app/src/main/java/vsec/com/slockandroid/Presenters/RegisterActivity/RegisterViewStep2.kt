package vsec.com.slockandroid.Presenters.RegisterActivity

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_2.*
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Controllers.PasswordEvaluator
import vsec.com.slockandroid.R
import vsec.com.slockandroid.generalModels.ButtonState
import vsec.com.slockandroid.generalModels.PasswordScore
import java.util.*


class RegisterViewStep2 : Activity(), RegisterPresenter.View {

    private lateinit var presenter: RegisterPresenter
    private var buttonState: EnumSet<ButtonState> = EnumSet.noneOf(ButtonState::class.java)
    var pBarVal: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_2)

        this.presenter = RegisterPresenter(this)

        btn_finish_reg.isEnabled = false

        var firstName = ""
        var lastName = ""
        var email = ""


        val extras = intent.extras
        if (extras != null) {
            firstName = extras.getString("firstName")!!
            lastName = extras.getString("lastName")!!
            email = extras.getString("email")!!
            pBarVal = extras.getString("pBarValue")!!.toInt()
        }

        reg_progressBar_step2.progress = pBarVal+1

        in_reg_passwd.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val passwdScore = PasswordEvaluator.gradePassword(p0.toString())
                val gradeEnum = Helpers.checkPasswordIsStrong(passwdScore)
                var bool = false
                if (passwdScore > 20){bool = true}
                    if (bool){buttonState.add(ButtonState.PASSWORD_VALID)}
                        else{buttonState.remove(ButtonState.PASSWORD_VALID)}
                updateButtonState()

                if (gradeEnum == PasswordScore.WEAK){
                        tx_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_weak)
                        passwdBar.progress = 1
                        passwdBar.setProgressTintList(ColorStateList.valueOf(Color.RED))
                    }
                if (gradeEnum == PasswordScore.AVERAGE){
                        tx_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_avg)
                        passwdBar.progress = 2
                        passwdBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW))
                    }
                if (gradeEnum == PasswordScore.STRONG){
                        tx_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_strong)
                        passwdBar.progress = 3
                        passwdBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN))
                    }
                if (gradeEnum == PasswordScore.MARVELOUS){
                        tx_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_marv)
                        passwdBar.progress = 4
                        passwdBar.setProgressTintList(ColorStateList.valueOf(Color.BLUE))
                }
                else{}
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        var t = Toast.makeText(this, "", Toast.LENGTH_SHORT)

        in_reg_conf_passwd.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val bool = presenter.assertEqual(p0.toString(), in_reg_passwd.text.toString())
                if (bool && buttonState.contains(ButtonState.PASSWORD_VALID)){buttonState.add(ButtonState.PASSWORD_EQUAL)
                t.setText(bool.toString())
                    t.show()
                }
                    else{buttonState.remove(ButtonState.PASSWORD_EQUAL)
                    t.setText(bool.toString())
                    t.show()}
                updateButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updateProgressbar(){
        reg_progressBar_step2.progress = pBarVal+1
        if (buttonState.contains(ButtonState.PASSWORD_VALID)){reg_progressBar_step2.progress++}
        else{reg_progressBar_step2.progress - 1}
        if (buttonState.contains(ButtonState.PASSWORD_EQUAL)){reg_progressBar_step2.progress = reg_progressBar_step2.progress + 1}
        else{reg_progressBar_step2.progress - 1}
    }

    fun updateButtonState(){
        btn_finish_reg.isEnabled = buttonState.contains(ButtonState.PASSWORD_VALID)
                && buttonState.contains(ButtonState.PASSWORD_EQUAL)
        updateProgressbar()
    }

    override fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String>) {}
}

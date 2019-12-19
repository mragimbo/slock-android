package vsec.com.slockandroid.Presenters.SettingsActivity

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.isEmpty
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_register_2.*
import kotlinx.android.synthetic.main.activity_register_2.passwdBar
import kotlinx.android.synthetic.main.activity_register_2.tx_passwd_strength
import kotlinx.android.synthetic.main.activity_settings.*
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Controllers.PasswordEvaluator
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView
import vsec.com.slockandroid.Presenters.RegisterActivity.RegisterPresenter
import vsec.com.slockandroid.R
import vsec.com.slockandroid.generalModels.ButtonState
import vsec.com.slockandroid.generalModels.PasswordScore
import java.util.*

class SettingsView : Activity(), SettingsPresenter.View {

    private lateinit var presenter: RegisterPresenter
    private var buttonState: EnumSet<ButtonState> = EnumSet.noneOf(ButtonState::class.java)
    private var passwd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //TODO("API controlling and old passwd handling")

        in_old_passwd.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0!!.isNotEmpty()){buttonState.add(ButtonState.LOGIN_BUTTON_OK)}
                else{buttonState.remove(ButtonState.LOGIN_BUTTON_OK)}
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        in_new_passwd.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val passwdScore = PasswordEvaluator.gradePassword(p0.toString())
                val gradeEnum = Helpers.checkPasswordIsStrong(passwdScore)
                updateButtonState()
                passwd = p0.toString()

                if (gradeEnum == PasswordScore.WEAK){
                    tx_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_weak)
                    passwdBar.progress = 1
                    passwdBar.setProgressTintList(ColorStateList.valueOf(Color.RED))
                    buttonState.remove(ButtonState.PASSWORD_VALID)
                }
                if (gradeEnum == PasswordScore.AVERAGE){
                    tx_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_avg)
                    passwdBar.progress = 2
                    passwdBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW))
                    buttonState.add(ButtonState.PASSWORD_VALID)
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
                    if (in_reg_passwd.text.length > 50){
                        tx_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_too_long)
                        passwdBar.progress = 0
                    }
                }
                else{
                    if (in_reg_passwd.text.isEmpty()){
                        tx_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_empty)
                        passwdBar.progress = 0}
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_new_conf_passwd.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val bool = presenter.assertEqual(p0.toString(), in_reg_passwd.text.toString())
                if (bool && buttonState.contains(ButtonState.PASSWORD_VALID)){buttonState.add(ButtonState.PASSWORD_EQUAL)}
                else{buttonState.remove(ButtonState.PASSWORD_EQUAL)}
                updateButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btn_settings_logout.setOnClickListener{
            changeActivity(LoginView::class.java as Class<Activity>)
            //TODO API CALL and handling
        }
        }
    fun updateButtonState(){
        btn_finish_reg.isEnabled = buttonState.contains(ButtonState.PASSWORD_VALID)
                && buttonState.contains(ButtonState.PASSWORD_EQUAL)
                && buttonState.contains(ButtonState.LOGIN_BUTTON_OK)
    }
    override fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String>) {
        startActivity(intent)
    }


}

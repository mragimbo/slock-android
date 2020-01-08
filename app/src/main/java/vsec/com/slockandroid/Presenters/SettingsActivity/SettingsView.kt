package vsec.com.slockandroid.Presenters.SettingsActivity

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Controllers.PasswordEvaluator
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView
import vsec.com.slockandroid.R
import vsec.com.slockandroid.generalModels.ButtonState
import vsec.com.slockandroid.generalModels.PasswordScore
import java.util.*

class SettingsView : Activity(), SettingsPresenter.View {

    private lateinit var presenter: SettingsPresenter
    private var buttonState: EnumSet<ButtonState> = EnumSet.noneOf(ButtonState::class.java)
    private var passwd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        btn_set_change_passwd.isEnabled = false
        this.presenter = SettingsPresenter(this)

        //TODO("API controlling and old passwd handling")

        in_old_passwd.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()){buttonState.add(ButtonState.LOGIN_BUTTON_OK)}
                else{buttonState.remove(ButtonState.LOGIN_BUTTON_OK)}
                updateButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        in_new_passwd.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val passwdScore = PasswordEvaluator.gradePassword(p0.toString())
                val gradeEnum = Helpers.checkPasswordIsStrong(passwdScore)
                passwd = p0.toString()

                if (gradeEnum == PasswordScore.WEAK){
                    tx_set_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_weak)
                    set_passwdBar.progress = 1
                    set_passwdBar.setProgressTintList(ColorStateList.valueOf(Color.RED))
                    buttonState.remove(ButtonState.PASSWORD_VALID)
                }
                if (gradeEnum == PasswordScore.AVERAGE){
                    tx_set_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_avg)
                    set_passwdBar.progress = 2
                    set_passwdBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW))
                    buttonState.add(ButtonState.PASSWORD_VALID)
                }
                if (gradeEnum == PasswordScore.STRONG){
                    tx_set_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_strong)
                    set_passwdBar.progress = 3
                    set_passwdBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN))
                }
                if (gradeEnum == PasswordScore.MARVELOUS){
                    tx_set_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_marv)
                    set_passwdBar.progress = 4
                    set_passwdBar.setProgressTintList(ColorStateList.valueOf(Color.BLUE))
                    if (in_new_passwd.text.length > 50){
                        tx_set_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_too_long)
                        set_passwdBar.progress = 0
                    }
                }
                else{
                    if (in_new_passwd.text.isEmpty()){
                        tx_set_passwd_strength.text = resources.getText(R.string.reg_screen2_passwd_empty)
                        set_passwdBar.progress = 0}
                }

                val bool = presenter.assertEqual(p0.toString(), in_new_conf_passwd.text.toString())
                if (bool && buttonState.contains(ButtonState.PASSWORD_VALID)){buttonState.add(ButtonState.PASSWORD_EQUAL)}
                else{buttonState.remove(ButtonState.PASSWORD_EQUAL)}


                if(in_old_passwd.toString().isNotEmpty() && in_old_passwd.text.toString().isNotEmpty()){buttonState.add(ButtonState.LOGIN_BUTTON_OK)}
                else{buttonState.remove(ButtonState.LOGIN_BUTTON_OK)}
                updateButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_new_conf_passwd.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                presenter = SettingsPresenter(this@SettingsView)
                val bool = presenter.assertEqual(p0.toString(), in_new_passwd.text.toString())
                if (bool && buttonState.contains(ButtonState.PASSWORD_VALID)){buttonState.add(ButtonState.PASSWORD_EQUAL)}
                else{buttonState.remove(ButtonState.PASSWORD_EQUAL)}

                var old = in_old_passwd.text.toString()
                if(in_old_passwd.toString().isNotEmpty() && in_old_passwd.text.toString().isNotEmpty()){buttonState.add(ButtonState.LOGIN_BUTTON_OK)}
                else{buttonState.remove(ButtonState.LOGIN_BUTTON_OK)}
                updateButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btn_settings_logout.setOnClickListener{
            changeActivity(LoginView::class.java as Class<Activity>)
            //TODO api call to logout session
        }

        btn_set_change_passwd.setOnClickListener{
            Toast.makeText(this,
                "Password changed Successfully",
                     Toast.LENGTH_LONG).show()
                    in_old_passwd.text.clear()
                    in_new_passwd.text.clear()
                    in_new_conf_passwd.text.clear()
                    updateButtonState()
        }
        }
    fun updateButtonState(){
        btn_set_change_passwd.isEnabled = buttonState.contains(ButtonState.PASSWORD_VALID)
                && buttonState.contains(ButtonState.PASSWORD_EQUAL)
                && buttonState.contains(ButtonState.LOGIN_BUTTON_OK)
    }

    override fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String>) {
        val intent: Intent = Intent(this, toActivity).apply{
            for(e in extra){
                putExtra(e.key, e.value)
            }
        }
        startActivity(intent)
    }


}

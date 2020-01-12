package vsec.com.slockandroid.Presenters.RegisterActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import vsec.com.slockandroid.R
import vsec.com.slockandroid.generalModels.ButtonState
import java.util.*


class RegisterView : Activity(), RegisterPresenter.View {


    private lateinit var presenter: RegisterPresenter
    private var buttonState: EnumSet<ButtonState> = EnumSet.noneOf(ButtonState::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.presenter = RegisterPresenter(this)

        btn_next.isEnabled = false
        reg_progressBar_step1.progress = 1

        var firstName: String = ""
        var lastName: String = ""
        var email: String = ""

        btn_next.setOnClickListener{
            var extras: MutableMap<String, String> = mutableMapOf()
            extras.put("firstName", firstName)
            extras.put("lastName", lastName)
            extras.put("email", email)
            extras.put("pBarValue", reg_progressBar_step1.progress.toString())
            this.changeActivity(RegisterViewStep2::class.java as Class<Activity>, extras)
        }

        in_firstName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val bool = presenter.checkNameField(p0.toString())
                 firstName = p0.toString()

                if(p0!!.isNotEmpty() && bool){ buttonState.add(ButtonState.FIRST_NAME_VALID) }
                else{buttonState.remove(ButtonState.FIRST_NAME_VALID)}
                updateButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_lastName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val bool = presenter.checkNameField(p0.toString())
                lastName = p0.toString()

                if(p0!!.isNotEmpty() && bool){ buttonState.add(ButtonState.LAST_NAME_VALID)}
                else{buttonState.remove(ButtonState.LAST_NAME_VALID)}
                updateButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_reg_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val bool = presenter.checkEmailValidity(p0.toString())
                email = p0.toString()

                if(bool){ buttonState.add(ButtonState.EMAIL_VALID)}
                else{buttonState.remove(ButtonState.EMAIL_VALID)}
                updateButtonState()

                val bool2 = this@RegisterView.presenter.assertEqual(p0.toString().trim(),
                    this@RegisterView.in_reg_conf_email.text.toString())
                if (bool2){buttonState.add(ButtonState.EMAIL_EQUAL)}
                else{buttonState.remove(ButtonState.EMAIL_EQUAL)}
                updateButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_reg_conf_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val bool = this@RegisterView.presenter.assertEqual(p0.toString().trim(),
                    this@RegisterView.in_reg_email.text.toString().trim())
                if (bool){buttonState.add(ButtonState.EMAIL_EQUAL)}
                else{buttonState.remove(ButtonState.EMAIL_EQUAL)}
                updateButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        }
    private fun updateProgressbar(){
        reg_progressBar_step1.progress = 1
        if (buttonState.contains(ButtonState.FIRST_NAME_VALID)){reg_progressBar_step1.progress++}
            else{reg_progressBar_step1.progress -1}
        if (buttonState.contains(ButtonState.LAST_NAME_VALID)){reg_progressBar_step1.progress++}
            else{reg_progressBar_step1.progress -1}
        if (buttonState.contains(ButtonState.EMAIL_VALID)){reg_progressBar_step1.progress++}
            else{reg_progressBar_step1.progress -1}
        if (buttonState.contains(ButtonState.EMAIL_EQUAL)){reg_progressBar_step1.progress++}
            else{reg_progressBar_step1.progress -1}


    }

    fun updateButtonState(){
        btn_next.isEnabled = buttonState.contains(ButtonState.FIRST_NAME_VALID)
                && buttonState.contains(ButtonState.LAST_NAME_VALID)
                && buttonState.contains(ButtonState.EMAIL_VALID)
                && buttonState.contains(ButtonState.EMAIL_EQUAL)
        updateProgressbar()
    }

    override fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String>) {
        val intent: Intent = Intent(this, toActivity).apply{
            for(e in extras){
                putExtra(e.key, e.value)
            }
        }
        startActivity(intent)
    }

    override fun toastLong(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

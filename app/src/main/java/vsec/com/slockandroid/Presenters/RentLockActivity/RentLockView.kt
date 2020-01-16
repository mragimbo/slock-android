package vsec.com.slockandroid.Presenters.RentLockActivity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_rent_lock.*
import vsec.com.slockandroid.R
import vsec.com.slockandroid.generalModels.ButtonState
import java.util.*

class RentLockView : AppCompatActivity(), RentLockPresenter.View {
    private lateinit var presenter: RentLockPresenter
    private var buttonState: EnumSet<ButtonState> = EnumSet.noneOf(ButtonState::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_lock)
        this.presenter = RentLockPresenter(this)

        val extras = intent.extras
        val id: String = extras!!.getString("lockId")
        this.presenter.updateLockId(id)

        btn_rent_lock.isEnabled = false

        in_rent_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val email: String = p0.toString()
                val hasValidEmail: Boolean = presenter.checkEmailValidity(email)

                presenter.updateEmail(email)

                if(hasValidEmail)
                    buttonState.add(ButtonState.EMAIL_VALID)
                else
                    buttonState.remove(ButtonState.EMAIL_VALID)

                val emailsAreEqual: Boolean = this@RentLockView.presenter.assertEqual(email.trim(), in_conf_rent_email.text.toString().trim())

                if (emailsAreEqual)
                    buttonState.add(ButtonState.EMAIL_EQUAL)
                else
                    buttonState.remove(ButtonState.EMAIL_EQUAL)
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_conf_rent_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val areEmailEqual: Boolean = this@RentLockView.presenter.assertEqual(p0.toString().trim(), in_conf_rent_email.text.toString().trim())
                if (areEmailEqual)
                    buttonState.add(ButtonState.EMAIL_EQUAL)
                else
                    buttonState.remove(ButtonState.EMAIL_EQUAL)

                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_start_date.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                presenter.updateStartDate(p0.toString())

                checkDateButtonState()
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_start_time.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                presenter.updateStartTime(p0.toString())

                checkDateButtonState()
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_end_date.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                presenter.updateEndDate(p0.toString())

                checkDateButtonState()
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_end_time.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                presenter.updateEndTime(p0.toString())
                checkDateButtonState()
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btn_rent_lock.setOnClickListener {
            this.presenter.sendRentLockRequestToApi()
        }
    }

    fun updateButtonState(){
        var b = buttonState.toString()
        btn_rent_lock.isEnabled = buttonState.contains(ButtonState.EMAIL_VALID)
                && buttonState.contains(ButtonState.EMAIL_EQUAL)
                && buttonState.contains(ButtonState.START_DATE_VALID)
                && buttonState.contains(ButtonState.END_DATE_VALID)
    }

    fun checkDateButtonState(){
        if(presenter.checkStartDateValidity())
            buttonState.add(ButtonState.START_DATE_VALID)
        else
            buttonState.remove(ButtonState.START_DATE_VALID)

        if(presenter.checkEndDateValidity())
            buttonState.add(ButtonState.END_DATE_VALID)
        else
            buttonState.remove(ButtonState.END_DATE_VALID)
    }

    override fun toastLong(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun changeActivity(toActivity: Class<Activity>, extras: Map<String, String>) {
        this.runOnUiThread {
            var intent: Intent = Intent(this, toActivity)
            for(extra in extras){
                intent.putExtra(extra.key, extra.value)
            }
            startActivity(intent)
        }
    }

}

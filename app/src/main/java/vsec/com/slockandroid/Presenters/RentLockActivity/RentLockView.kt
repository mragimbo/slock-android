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
    private var renter_email = ""


    private lateinit var presenter: RentLockPresenter
    private var buttonState: EnumSet<ButtonState> = EnumSet.noneOf(ButtonState::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_lock)

        this.presenter = RentLockPresenter(this)

            btn_rent_lock.isEnabled = false

        in_rent_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val bool = presenter.checkEmailValidity(p0.toString())
                renter_email = p0.toString()

                if(bool){ buttonState.add(ButtonState.EMAIL_VALID)}
                else{buttonState.remove(ButtonState.EMAIL_VALID)}
                updateButtonState()

                val bool2 = this@RentLockView.presenter.assertEqual(p0.toString().trim(),
                    in_conf_rent_email.text.toString())
                if (bool2){buttonState.add(ButtonState.EMAIL_EQUAL)}
                else{buttonState.remove(ButtonState.EMAIL_EQUAL)}
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_conf_rent_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val bool = this@RentLockView.presenter.assertEqual(p0.toString().trim(),
                    in_conf_rent_email.text.toString().trim())
                if (bool){buttonState.add(ButtonState.EMAIL_EQUAL)}
                else{buttonState.remove(ButtonState.EMAIL_EQUAL)}
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        in_start_date.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        in_start_time.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        in_end_date.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        in_end_time.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    fun updateButtonState(){
        btn_rent_lock.isEnabled = buttonState.contains(ButtonState.EMAIL_VALID)
                && buttonState.contains(ButtonState.EMAIL_EQUAL)
                && buttonState.contains(ButtonState.START_DATE_VALID)
                && buttonState.contains(ButtonState.START_TIME_VALID)
                && buttonState.contains(ButtonState.END_DATE_VALID)
                && buttonState.contains(ButtonState.END_TIME_VALID)
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

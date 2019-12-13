package vsec.com.slockandroid.Presenters.RegisterActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import kotlinx.android.synthetic.main.activity_register.*
import vsec.com.slockandroid.R


class RegisterView : Activity(), RegisterPresenter.View {


    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.presenter = RegisterPresenter(this)

        //btn_next.isEnabled = false

        var firstName: String = ""
        var lastName: String = ""
        var email: String = ""

        btn_next.setOnClickListener{
            var extras: MutableMap<String, String> = mutableMapOf()
            extras.put("firstName", firstName)
            extras.put("lastName", lastName)
            extras.put("email", email)
            this.changeActivity(RegisterViewStep2::class.java as Class<Activity>, extras)
        }


        val t = Toast.makeText(this,"changed", LENGTH_LONG)

        in_firstName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val bool = presenter.checkNameField(p0.toString())
                t.setText(bool.toString())
                t.show()
                 firstName = p0.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        in_lastName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val bool = presenter.checkNameField(p0.toString())
                t.setText(bool.toString())
                t.show()
                lastName = p0.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        in_reg_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val bool = presenter.checkEmailValidity(p0.toString())
                t.setText(bool.toString())
                t.show()
                email = p0.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        in_reg_conf_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val bool = this@RegisterView.presenter.assertEqual(p0.toString().trim(),
                    this@RegisterView.in_reg_email.text.toString().trim())
                t.setText(bool.toString())
                t.show()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        }

    override fun changeActivity(toActivity: Class<Activity>, extras: Map<String, String>) {
        val intent: Intent = Intent(this, toActivity).apply{
            for(extra in extras){
                putExtra(extra.key, extra.value)
            }
        }
        startActivity(intent)
    }

}

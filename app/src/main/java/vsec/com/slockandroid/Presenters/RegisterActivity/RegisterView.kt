package vsec.com.slockandroid.Presenters.RegisterActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*
import vsec.com.slockandroid.R


class RegisterView : Activity(), RegisterPresenter.View {

    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.presenter = RegisterPresenter(this)

        btn_next.setOnClickListener{
            this.changeActivity(RegisterViewStep2::class.java as Class<Activity>)
        }
    }
    override fun changeActivity(toActivity: Class<Activity>, extras: Map<String, String>) {
        var intent: Intent = Intent(this, toActivity)
        for(extra in extras){
            intent.putExtra(extra.key, extra.value)
        }
        startActivity(intent)
    }

}

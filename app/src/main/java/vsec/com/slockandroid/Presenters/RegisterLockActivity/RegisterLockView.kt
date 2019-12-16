package vsec.com.slockandroid.Presenters.RegisterLockActivity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_owned_locks.*
import vsec.com.slockandroid.Presenters.OwnedLocksActivity.OwnedLocksActivity
import vsec.com.slockandroid.R

class RegisterLockView : Activity(), RegisterLockPresenter.View{

    private lateinit var presenter: RegisterLockPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_lock)

        this.presenter = RegisterLockPresenter(this)
        this.presenter.lookForRegisterableLock()

        btn_register_lock.setOnClickListener{
            this.presenter.connectToRegisterableLock()
        }
    }

    override fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRegisterableLockFound(){
        btn_register_lock.isEnabled = true
    }
}

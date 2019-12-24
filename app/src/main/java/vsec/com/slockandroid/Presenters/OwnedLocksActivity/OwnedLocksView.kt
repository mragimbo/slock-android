package vsec.com.slockandroid.Presenters.OwnedLocksActivity

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_owned_locks.*

import vsec.com.slockandroid.Presenters.RegisterLockActivity.RegisterLockView
import vsec.com.slockandroid.Presenters.RegisterLockActivity.RegisterLockPresenter
import vsec.com.slockandroid.R

class OwnedLocksView : Activity(), RegisterLockPresenter.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owned_locks)


        btn_add_lock.setOnClickListener {
            var extras: MutableMap<String, String> = mutableMapOf()
            extras.put("empty_field", "")
            changeActivity(RegisterLockView::class.java as Class<Activity>, extras)

        }
    }

    override fun changeActivity(toActivity: Class<Activity>, extras: Map<String, String>) {
        val intent: Intent = Intent(this, toActivity).apply{
            for(e in extras){
                putExtra(e.key, e.value)
            }
        }
        startActivity(intent)
    }

    override fun onRegisterableLockFound(lock: BluetoothDevice) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNoRegisterableDeviceFound() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disableLoader() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

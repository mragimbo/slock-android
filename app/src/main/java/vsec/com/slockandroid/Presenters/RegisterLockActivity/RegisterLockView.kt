package vsec.com.slockandroid.Presenters.RegisterLockActivity

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.btn_register_lock
import kotlinx.android.synthetic.main.activity_register_lock.*
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.R
import vsec.com.slockandroid.generalModels.ButtonState
import java.util.*


class RegisterLockView : Activity(), RegisterLockPresenter.View {
    override fun getContext(): Context {
        return this
    }

    private var buttonState: EnumSet<ButtonState> = EnumSet.noneOf(ButtonState::class.java)
    private lateinit var presenter: RegisterLockPresenter
    private lateinit var lock: BluetoothDevice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_lock)

        this.presenter = RegisterLockPresenter(this)
        this.presenter.lookForRegistrableLock()

        btn_register_lock.setOnClickListener{
            this.presenter.registerLock(this.lock)
        }

        in_lock_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    buttonState.add(ButtonState.LOCK_NAME_VALID)
                }else{
                    buttonState.remove(ButtonState.LOCK_NAME_VALID)
                }
                presenter.updateLockName(p0.toString())
                updateButtonState()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        in_lock_productkey.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty() && Helpers.isValidProductKeyFormat(p0.toString())){
                    buttonState.add(ButtonState.LOCK_PRODUCTKEY_VALID)
                }else{
                    buttonState.remove(ButtonState.LOCK_PRODUCTKEY_VALID)
                }
                presenter.updateProductKey(p0.toString())
                updateButtonState()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        in_lock_description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    buttonState.add(ButtonState.LOCK_DESCRIPTION_VALID)
                }else{
                    buttonState.remove(ButtonState.LOCK_DESCRIPTION_VALID)
                }
                presenter.updateDescription(p0.toString())
                updateButtonState()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun updateButtonState(){
        btn_register_lock.isEnabled = (
                buttonState.contains(ButtonState.LOCK_NAME_VALID) &&
                buttonState.contains(ButtonState.REGISTERABLE_LOCK_FOUND) &&
                buttonState.contains(ButtonState.LOCK_DESCRIPTION_VALID) &&
                buttonState.contains(ButtonState.LOCK_PRODUCTKEY_VALID)
        )
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

    override fun onRegisterableLockFound(lock: BluetoothDevice){
        this.lock = lock
        buttonState.add(ButtonState.REGISTERABLE_LOCK_FOUND)
        Toast.makeText(this,R.string.registerable_device_found, Toast.LENGTH_SHORT).show()
        this.updateButtonState()
        img_spinner.visibility = View.INVISIBLE
    }

    override fun onNoRegisterableDeviceFound() {
        Toast.makeText(this,R.string.registerable_device_not_found, Toast.LENGTH_SHORT).show()
        img_spinner.visibility = View.INVISIBLE
    }

    override fun checkLock() {
        this.runOnUiThread {
            tv_connected_to_lock.text = "Waiting for lock to boot up"
        }
    }
}

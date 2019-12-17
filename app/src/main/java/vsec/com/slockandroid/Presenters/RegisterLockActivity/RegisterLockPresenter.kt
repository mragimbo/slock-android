package vsec.com.slockandroid.Presenters.RegisterLockActivity

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.util.Log
import vsec.com.slockandroid.Controllers.BluetoothController
import vsec.com.slockandroid.Controllers.Callback.BluetoothLockRegister
import vsec.com.slockandroid.Controllers.Callback.BluetoothScanCallback
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.generalModels.ButtonState
import vsec.com.slockandroid.generalModels.Lock
import java.util.*

class RegisterLockPresenter (private val view: RegisterLockPresenter.View){
    private val lock: Lock = Lock()


    fun lookForRegistrableLock() {
        BluetoothController.scanLeDevice(true, ::onScanDone)
    }

    fun registerLock(lock: BluetoothDevice) {
        this.lock.setUuid(Helpers.newBase64Uuid())
        this.lock.setSecret(Helpers.newBase64Token())
        lock.connectGatt(BluetoothController.context,false, BluetoothLockRegister(this.lock, ::onRegistrationDone))
    }

    fun onRegistrationDone(){
        this.view.changeActivity(HomeView::class.java as Class<Activity>)
    }


    fun onScanDone(){
        val lock: BluetoothDevice? = BluetoothScanCallback.scannedBleDevices.filter { it.name != null }.find { it.name.contains("SLOCK") }
        if(lock != null){
            Log.e("b", lock.name)
            view.onRegisterableLockFound(lock)
        }
    }

    interface View {
        fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String> = emptyMap())
        fun onRegisterableLockFound(lock: BluetoothDevice)
    }
}
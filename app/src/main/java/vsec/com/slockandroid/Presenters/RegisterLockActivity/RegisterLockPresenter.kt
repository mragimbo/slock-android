package vsec.com.slockandroid.Presenters.RegisterLockActivity

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.util.Log
import vsec.com.slockandroid.Controllers.BluetoothController
import vsec.com.slockandroid.Controllers.Callback.BluetoothLockRegister
import vsec.com.slockandroid.Controllers.Callback.BluetoothScanCallback
import vsec.com.slockandroid.Controllers.Helpers

class RegisterLockPresenter (private val view: RegisterLockPresenter.View){
    fun lookForRegisterableLock() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun connectToRegisterableLock() {
        BluetoothController.scanLeDevice(true, ::onScanDone)

    }

    private fun registerLock(lock: BluetoothDevice){
        var uuid: String = Helpers.
        lock?.connectGatt(BluetoothController.context,false, BluetoothLockRegister())
    }



    fun onScanDone(){
        var lock: BluetoothDevice? = BluetoothScanCallback.scannedBleDevices.find { it.name.contains("SLOCK") }
        if(lock != null){
            Log.e("b", lock.name)
            this.registerLock(lock)
        }
    }

    interface View {
        fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String>)
        fun onRegisterableLockFound()
    }
}
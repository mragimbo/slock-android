package vsec.com.slockandroid.Presenters.RegisterLockActivity

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.BluetoothController
import vsec.com.slockandroid.Controllers.Callback.BluetootLockValidate
import vsec.com.slockandroid.Controllers.Callback.BluetoothLockRegister
import vsec.com.slockandroid.Controllers.Callback.BluetoothScanCallback
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.generalModels.Lock

class RegisterLockPresenter (private val view: RegisterLockPresenter.View){
    private val lock: Lock = Lock()


    fun lookForRegistrableLock() {
        BluetoothController.scanLeDevice(true, ::onScanDoneRegister)
    }

    fun registerLock(lock: BluetoothDevice) {
        this.lock.setUuid(Helpers.newBase64Uuid())
        this.lock.setBleAddress(lock.address)
        this.lock.setSecret(Helpers.newBase64Token())
        ApiController.registerLock(this.lock)
        lock.connectGatt(this.view.getContext(),false, BluetoothLockRegister(this.lock, ::onRegistrationDone))
    }

    fun onRegistrationDone() {
        this.view.checkLock()
        BluetoothController.scanLeDevice(true, ::onScanDoneValidate)
        //search for the device, if found. add lock to backend
        //this.view.changeActivity(HomeView::class.java as Class<Activity>)
    }

    fun onScanDoneRegister(){
        val lock: BluetoothDevice? = BluetoothScanCallback.scannedBleDevices.filter { it.name != null }.find { it.name.contains("SLOCK") } //&& it.address == "30:AE:A4:CE:F9:0E"  }//"SLOCK-ALPHA-v1") }
        if(lock != null){
            view.onRegisterableLockFound(lock)
        }else {
            this.view.onNoRegisterableDeviceFound()
        }
    }

    fun onScanDoneValidate(){
        BluetoothScanCallback.scannedBleDevices.find { it.address == this.lock.getBleAddress()}
            ?.connectGatt(this.view.getContext(),false,
            BluetootLockValidate(::onLockValidationDone)
        )
    }

    fun onLockValidationDone(validated: Boolean){
        if(validated){
            this.view.changeActivity(HomeView::class.java as Class<Activity>)
        }else{
            println("error")
        }
    }

    fun updateLockName(lockName: String) { this.lock.setName(lockName)}
    fun updateDescription(description: String){ this.lock.setDiscription(description) }
    fun updateProductKey(productKey: String) {
        this.lock.setProductKey(productKey)
    }

    interface View {
        fun changeActivity(toActivity: Class<Activity>, extras: Map<String, String> = emptyMap())
        fun onRegisterableLockFound(lock: BluetoothDevice)
        fun onNoRegisterableDeviceFound()
        fun checkLock()
        fun getContext(): Context
    }
}
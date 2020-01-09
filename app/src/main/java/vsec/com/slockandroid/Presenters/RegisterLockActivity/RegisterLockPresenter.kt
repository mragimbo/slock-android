package vsec.com.slockandroid.Presenters.RegisterLockActivity

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.util.Log
import vsec.com.slockandroid.Controllers.ApiController
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
    private lateinit var coutry: String
    private lateinit var city: String
    private lateinit var street: String
    private lateinit var streetNumber: String


    fun lookForRegistrableLock() {
        BluetoothController.scanLeDevice(true, ::onScanDoneRegister)
    }

    fun registerLock(lock: BluetoothDevice) {
        this.lock.setUuid(Helpers.newBase64Uuid())
        this.lock.setSecret(Helpers.newBase64Token())
        this.lock.setDiscription(this.coutry, this.city, this.street, this.streetNumber)
        ApiController.registerLock(this.lock)
        lock.connectGatt(BluetoothController.context,false, BluetoothLockRegister(this.lock, ::onRegistrationDone))
    }

    fun onRegistrationDone() {
        this.view.checkLock()
        BluetoothController.scanLeDevice(true, ::onScanDoneValidate)
        //search for the device, if found. add lock to backend
        //this.view.changeActivity(HomeView::class.java as Class<Activity>)
    }

    fun onScanDoneRegister(){
        val lock: BluetoothDevice? = BluetoothScanCallback.scannedBleDevices.filter { it.name != null }.find { it.name.contains("SLOCK") }
        if(lock != null){
            view.onRegisterableLockFound(lock)
        }else {
            this.view.onNoRegisterableDeviceFound()
        }
    }

    fun onScanDoneValidate(){
        val lock: BluetoothDevice? = BluetoothScanCallback.scannedBleDevices.filter { it.name != null }.find { it.name.equals("SLOCK_" + this.lock.getUuid()) }
        if(lock != null){
            this.view.changeActivity(HomeView::class.java as Class<Activity>)
        }
    }
    fun updateLockName(lockName: String) { this.lock.setName(lockName)}
    fun updateLockCountry(coutry: String){ this.coutry = coutry }
    fun updateLockCity(city: String){ this.city = city }
    fun updateLockStreet(street: String){ this.street = street }
    fun updateLockStreetNumber(streetNumber: String){ this.streetNumber = streetNumber }

    interface View {
        fun changeActivity(toActivity: Class<Activity>, extras: Map<String, String> = emptyMap())
        fun onRegisterableLockFound(lock: BluetoothDevice)
        fun onNoRegisterableDeviceFound()
        fun checkLock()
    }
}
package vsec.com.slockandroid.Presenters.RegisterLockActivity

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.AsyncTask
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.BluetoothController
import vsec.com.slockandroid.Controllers.Callback.BluetootLockValidate
import vsec.com.slockandroid.Controllers.Callback.BluetoothLockRegister
import vsec.com.slockandroid.Controllers.Callback.BluetoothScanCallback
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView
import vsec.com.slockandroid.generalModels.Lock

class RegisterLockPresenter (private val view: RegisterLockPresenter.View){
    private val lock: Lock = Lock()
    private lateinit var registerLockTask: RegisterLockTask
    private lateinit var bleLock: BluetoothDevice


    fun lookForRegistrableLock() {
        BluetoothController.scanLeDevice(true, ::onScanDoneRegister)
    }

    fun registerLock() {
        this.lock.setUuid(Helpers.newBase64Uuid())
        this.lock.setBleAddress(bleLock.address)
        this.lock.setSecret(Helpers.newBase64Token())
        this.registerLockTask = RegisterLockTask(this, bleLock)
        this.registerLockTask.execute(this.lock)
    }

    private fun lockRegisterdAtApi(lock: BluetoothDevice) {
        lock.connectGatt(this.view.getContext(),false, BluetoothLockRegister(this.lock, ::onRegistrationDone), BluetoothDevice.TRANSPORT_LE)
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
            this.bleLock = lock
            view.onRegisterableLockFound()
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
        fun onRegisterableLockFound()
        fun onNoRegisterableDeviceFound()
        fun checkLock()
        fun getContext(): Context
        fun toastLong(message: String)
    }

    companion object{
        class RegisterLockTask(
            private var presenter: RegisterLockPresenter,
            private var lock: BluetoothDevice
        ): AsyncTask<Lock,Void,String>(){
            override fun doInBackground(vararg params: Lock?): String {
                if(params.isEmpty())
                    return "500"
                return ApiController.registerLock(params[0] as Lock)
            }

            override fun onPostExecute(result: String?) {
                if(result == "200")
                    this.presenter.lockRegisterdAtApi(lock)
                else if(result == "401"){
                    ApiController.clearSession()
                    this.presenter.view.changeActivity(LoginView::class.java as Class<Activity>)
                }else{
                    this.presenter.view.toastLong("could not register the slock")
                }
            }
        }
    }
}
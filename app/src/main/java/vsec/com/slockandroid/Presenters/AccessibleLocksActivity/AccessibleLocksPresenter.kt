package vsec.com.slockandroid.Presenters.AccessibleLocksActivity

import BluetoothCommandCallback
import android.bluetooth.BluetoothDevice
import android.os.AsyncTask
import android.util.Log
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseList
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.BluetoothController
import vsec.com.slockandroid.Controllers.Callback.BluetoothScanCallback
import vsec.com.slockandroid.Controllers.LockAuthController
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView
import vsec.com.slockandroid.generalModels._LocksOverviewPresenter

class AccessibleLocksPresenter(override val view: _LocksOverviewPresenter.View) : _LocksOverviewPresenter {
    private var lockData: List<vsec.com.slockandroid.generalModels.Lock> = emptyList()

    private var lockAuthController: LockAuthController = LockAuthController(this)

    override fun onScanDone(lock: vsec.com.slockandroid.generalModels.Lock, command: String){
        if(lock.getBleAddress() == null) {
            this.view.toastLong("something went wrong")
            return
        }
        var lockUuid: String = lock.getBleAddress() as String
        val bleDevice: BluetoothDevice? = BluetoothScanCallback.scannedBleDevices.find { it.address == lockUuid }
        bleDevice?.connectGatt(BluetoothController.context,false, BluetoothCommandCallback(lock, command, ::onNotification))
    }

    @ImplicitReflectionSerializer
    override fun setLocks(result: String) {
        this.lockData = Json.parseList(result)
        this.view.refreshList(this.lockData)
    }

    fun fetchAccessibleLocks() {
        this.lockAuthController.executeGetLocks()
    }

    fun executeCommand(lock: vsec.com.slockandroid.generalModels.Lock, command: Int) {
        this.lockAuthController.executeLockCommand(lock,command)
    }
    
    private fun onNotification(lock: vsec.com.slockandroid.generalModels.Lock, status: String){
        if (status.startsWith("200")){
            if(lock.getId() != null){
                this.lockAuthController.executeRatchetTick(lock.getId() as Int)
            }
            //ratchetTickTask.execute(lock.getId())
            //ratchetTickTask = RatchetTickTask(this.view)
        }else{
            //do sync call
        }
    }
}
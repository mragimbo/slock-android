package vsec.com.slockandroid.Presenters.AccessibleLocksActivity

import BluetoothCommandCallback
import android.bluetooth.BluetoothDevice
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseList
import vsec.com.slockandroid.Controllers.BluetoothController
import vsec.com.slockandroid.Controllers.Callback.BluetoothScanCallback
import vsec.com.slockandroid.Controllers.LockAuthController
import vsec.com.slockandroid.generalModels.Lock
import vsec.com.slockandroid.generalModels._LocksOverviewPresenter
import java.lang.Exception

class AccessibleLocksPresenter(override val view: _LocksOverviewPresenter.View) : _LocksOverviewPresenter {
    private var lockData: List<Lock> = emptyList()
    private lateinit var changeButtonState: (viewHolder: AccessibleLocksRecyclerAdapter.ViewHolder, state: Boolean) -> Unit
    private lateinit var viewHolder: AccessibleLocksRecyclerAdapter.ViewHolder

    private var lockAuthController: LockAuthController = LockAuthController(this)

    override fun onScanDone(lock: Lock, command: String){
        if(lock.getBleAddress() == null) {
            this.view.toastLong("something went wrong")
            this.view.refreshList(this.lockData)
            return
        }
        var lockUuid: String = lock.getBleAddress() as String
        val bleDevice: BluetoothDevice? = BluetoothScanCallback.scannedBleDevices.find { it.address == lockUuid }
        if(bleDevice != null) {
            try {
                bleDevice.connectGatt(
                    BluetoothController.context,
                    false,
                    BluetoothCommandCallback(lock, command, ::onNotification)
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            this.view.toastLong("something went wrong")
            this.view.refreshList(this.lockData)
        }
    }

    @ImplicitReflectionSerializer
    override fun setLocks(result: String) {
        val joined: ArrayList<Lock> = ArrayList()
        joined.addAll(this.lockData)
        joined.addAll(Json.parseList<Lock>(result))
        this.lockData = joined
        this.view.refreshList(this.lockData)
    }

    fun fetchAccessibleLocks() {
        this.lockAuthController.executeGetLocks("/rentedlockes")
        this.lockAuthController.executeGetLocks("/ownedlocks")
        this.lockData = emptyList()

    }

    fun executeCommand(lock: Lock, command: Int, viewHolder: AccessibleLocksRecyclerAdapter.ViewHolder, changeButtonState:(view: AccessibleLocksRecyclerAdapter.ViewHolder, state: Boolean) -> Unit) {
        this.changeButtonState = changeButtonState
        this.viewHolder = viewHolder
        this.lockAuthController.executeLockCommand(lock,command)
    }

    private fun onNotification(lock: Lock, status: String){
        if (status.startsWith("200")){
            if(lock.getId() != null){
                this.lockAuthController.executeRatchetsync(lock.getId() as Int, status)
            }
        }else if(status.startsWith("401")){
            this.lockAuthController.executeRatchetsync(lock.getId() as Int,status)
            this.view.toastLong("Lock synced up try again")
            //do sync call
        }
        this.view.refreshList(this.lockData)
    }
}
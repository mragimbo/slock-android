package vsec.com.slockandroid.Presenters.AccessibleLocksActivity

import BluetoothCommandCallback
import android.bluetooth.BluetoothDevice
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseList
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.BluetoothController
import vsec.com.slockandroid.Controllers.Callback.BluetoothScanCallback
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView
import java.util.concurrent.locks.Lock

class AccessibleLocksPresenter(private val view: View) {
    private var lock_data: List<vsec.com.slockandroid.generalModels.Lock> = emptyList()

    private var getLocksTask: GetLocksTask
    private lateinit var executeLockCommandTask: ExecuteLockCommandTask
    private var ratchetTickTask: RatchetTickTask

    init {
        getLocksTask = GetLocksTask(this)
        ratchetTickTask = RatchetTickTask(this.view)
    }

    fun fetchAccessibleLocks() {
        this.getLocksTask.execute()
        this.getLocksTask = GetLocksTask(this)
    }


    @ImplicitReflectionSerializer
    private fun setLocks(result: String) {
        this.lock_data = Json.parseList(result)
        this.view.refreshList(this.lock_data)
    }

    fun executeCommand(lock: vsec.com.slockandroid.generalModels.Lock, i: Int) {
        this.executeLockCommandTask = ExecuteLockCommandTask(lock, this)
        this.executeLockCommandTask.execute(i)
    }
    
    fun onNotification(lock: vsec.com.slockandroid.generalModels.Lock, command: String){
        if (command.startsWith("200")){
            ratchetTickTask.execute(lock.getId())
            ratchetTickTask = RatchetTickTask(this.view)
            //do ratchetTick
        }else{
            //do sync call
        }
    }

    fun onScanDone(lock: vsec.com.slockandroid.generalModels.Lock, command: String){
        if(lock.getBleAddress() == null) {
            this.view.toastLong("something went wrong")
            return
        }
        var lockuuid: String = lock.getBleAddress() as String
        val bleDevice: BluetoothDevice? = BluetoothScanCallback.scannedBleDevices.find { it.address == lockuuid }

        bleDevice?.connectGatt(BluetoothController.context,false, BluetoothCommandCallback(lock, command, ::onNotification))

    }

    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String> = HashMap())
        fun toastLong(message: String)
        fun refreshList(locks: List<vsec.com.slockandroid.generalModels.Lock>)
    }

    companion object{
        class GetLocksTask(private var presenter: AccessibleLocksPresenter) : AsyncTask<Void, Void, String>() {

            override fun doInBackground(vararg params: Void?): String? {
                //returns responsebody, else responsecode
                return ApiController.GetAccessibleLocks()
            }

            @ImplicitReflectionSerializer
            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                when (result) {
                    "400" -> {
                        this.presenter.view.changeActivity(LoginView::class.java)
                        ApiController.clearSession()
                    }
                    "401" -> {
                        this.presenter.view.changeActivity(LoginView::class.java)
                        ApiController.clearSession()
                    }
                    "500" -> this.presenter.view.toastLong("Something went wrong")
                    "502" -> this.presenter.view.toastLong("Something went wrong")
                    else -> this.presenter.setLocks(result)
                }
            }
        }

        class ExecuteLockCommandTask(private val lock: vsec.com.slockandroid.generalModels.Lock, private val presenter: AccessibleLocksPresenter) : AsyncTask<Int, Void, String>() {
            override fun doInBackground(vararg params: Int?): String? {
                return ApiController.GetLockToken(this.lock, params[0])
            }

            @ImplicitReflectionSerializer
            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                when (result) {
                    "400" -> {
                        this.presenter.view.changeActivity(LoginView::class.java)
                        ApiController.clearSession()
                    }
                    "401" -> {
                        this.presenter.view.changeActivity(LoginView::class.java)
                        ApiController.clearSession()
                    }
                    "500" -> this.presenter.view.toastLong("Something went wrong")
                    else -> {
                        if(result.length < 90){
                            //this is not aan command send error
                            this.presenter.view.toastLong("Error")
                        }
                        BluetoothController.scanLeDevice(true) { this.presenter.onScanDone(this.lock, result) }
                    }
                }
            }
        }

        class RatchetTickTask(private var view: AccessibleLocksPresenter.View): AsyncTask<Int, Void, String>(){
            override fun doInBackground(vararg params: Int?): String {
                if(params.isNotEmpty())
                    return ApiController.DoRatchetTick(params[0] as Int)
                return "400"
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                when (result) {
                    "200" -> {
                        Log.e("ratchet syns", "ratchet synced")
                    }
                    "400" -> {
                        this.view.changeActivity(LoginView::class.java)
                        ApiController.clearSession()
                    }
                    "401" -> {
                        this.view.changeActivity(LoginView::class.java)
                        ApiController.clearSession()
                    }
                    "500" -> this.view.toastLong("Something went wrong")
                    else -> {
                        this.view.toastLong("Unexpected result")
                    }
                }
            }

        }
    }
}
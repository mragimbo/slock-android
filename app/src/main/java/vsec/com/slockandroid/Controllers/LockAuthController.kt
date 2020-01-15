package vsec.com.slockandroid.Controllers

import android.os.AsyncTask
import android.util.Log
import kotlinx.serialization.ImplicitReflectionSerializer
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView
import vsec.com.slockandroid.generalModels.Lock
import vsec.com.slockandroid.generalModels._LocksOverviewPresenter

open class LockAuthController(private var presenter: _LocksOverviewPresenter){

    private lateinit var getLocksTask: GetLocksTask
    private lateinit var executeLockCommandTask: ExecuteLockCommandTask
    private lateinit var ratchetTickTask: RatchetTickTask


    fun executeGetLocks(){
        this.getLocksTask = GetLocksTask(presenter)
        this.getLocksTask.execute()
    }

    fun executeLockCommand(lock: Lock, command: Int){
        this.executeLockCommandTask = ExecuteLockCommandTask(lock, presenter)
        this.executeLockCommandTask.execute(command)
    }

    fun executeRatchetTick(lockId: Int){
        this.ratchetTickTask = RatchetTickTask(presenter)
        this.ratchetTickTask.execute(lockId)
    }

    companion object{
        class GetLocksTask(private var presenter: _LocksOverviewPresenter) : AsyncTask<Void, Void, String>() {

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

        class ExecuteLockCommandTask(
            private val lock: Lock,
            private val presenter: _LocksOverviewPresenter
        ) : AsyncTask<Int, Void, String>() {

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
                            //this is not an command send error
                            this.presenter.view.toastLong("Error")
                        }
                        BluetoothController.scanLeDevice(true) { this.presenter.onScanDone(this.lock, result) }
                    }
                }
            }
        }

        class RatchetTickTask(private var presenter: _LocksOverviewPresenter): AsyncTask<Int, Void, String>(){

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
                        this.presenter.view.changeActivity(LoginView::class.java)
                        ApiController.clearSession()
                    }
                    "401" -> {
                        this.presenter.view.changeActivity(LoginView::class.java)
                        ApiController.clearSession()
                    }
                    "500" -> this.presenter.view.toastLong("Something went wrong")
                    else -> {
                        this.presenter.view.toastLong("Unexpected result")
                    }
                }
            }

        }
    }
}
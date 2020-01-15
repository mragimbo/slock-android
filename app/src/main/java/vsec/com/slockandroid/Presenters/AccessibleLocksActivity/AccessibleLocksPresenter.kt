package vsec.com.slockandroid.Presenters.AccessibleLocksActivity

import android.os.AsyncTask
import android.widget.Toast
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseList
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Presenters.LoginActivity.LoginView

class AccessibleLocksPresenter(private val view: View) {
    private var lock_data: List<vsec.com.slockandroid.generalModels.Lock> = emptyList()

    private var getLocksTask: GetLocksTask
    private lateinit var executeLockCommandTask: ExecuteLockCommandTask

    init {
        getLocksTask = GetLocksTask(this)
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
        /*when(i){
            1 -> //open
            0 -> //idle
            -1 -> //close
        }*/
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
                    else -> this.presenter.setLocks(result)
                }
            }
        }

        class ExecuteLockCommandTask(private val lock: vsec.com.slockandroid.generalModels.Lock, private val presenter: AccessibleLocksPresenter) : AsyncTask<Int, Void, String>() {
            override fun doInBackground(vararg params: Int?): String? {
                //returns responsebody, else responsecode
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
                        }
                        this.presenter.view.toastLong(result)
                        //send this result over ble
                    }
                }
            }
        }
    }
}
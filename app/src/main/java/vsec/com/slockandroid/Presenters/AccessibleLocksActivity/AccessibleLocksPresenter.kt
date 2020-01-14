package vsec.com.slockandroid.Presenters.AccessibleLocksActivity

import android.os.AsyncTask
import android.util.Log
import vsec.com.slockandroid.Controllers.ApiController

 private var lock_data: String = ""

class AccessibleLocksPresenter(private val view: View) {
    private var getLocksTask: GetLocksTask


    init {
        getLocksTask = GetLocksTask(view)
    }

    fun GetAccessibleLocks(): String{
        this.getLocksTask.execute()
        this.getLocksTask = GetLocksTask(this.view)
        return lock_data
    }

    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String> = HashMap())
        fun toastLong(message: String)
    }

    companion object{
        class GetLocksTask(private var view: AccessibleLocksPresenter.View) : AsyncTask<Void, Void, String>() {

            override fun doInBackground(vararg params: Void?): String? {
                //returns responsebody, else responsecode
                return ApiController.GetAccessibleLocks()
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                when (result) {
                    //TODO for troubleshooting, delete before deployment
                    result -> Log.e("JSON_data", result)
                    result -> lock_data = result
                }
            }
        }
    }
}
package vsec.com.slockandroid.Presenters.AccessibleLocksActivity

import android.os.AsyncTask
import java.util.concurrent.locks.Lock

class AccessibleLocksPresenter(private val view: View) {

    private var locks =  HashMap<String, String>()
    private var getLocksTaks: GetLocksTask = GetLocksTask(this)
    private var getLockTokenTask: GetLockTokenTask = GetLockTokenTask(this)
    init {
        locks.put("lock_1","token_1")
        locks.put("lock_2", "token_2")
        locks.put("lock_3", "token_3")
    }

    fun getLocks(): HashMap<String, String> {
        return this.locks
    }

    fun getLockToken(){

    }

    fun pullLocks(){
        TODO("Call this on page reload")
        //this.task.execute()
        //this.task = Task(this)
    }

    fun executeLockCommand(command: Int) {
        //this.getlocktokentask execute
    }


    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String> = HashMap())
        fun toastLong(message: String)
    }

    companion object {
        class GetLocksTask(private var presenter: AccessibleLocksPresenter): AsyncTask<Void, Void, Lock>() {
            override fun doInBackground(vararg params: Void?): Lock {
                TODO("DoApiCall here") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPostExecute(result: Lock?) {
                super.onPostExecute(result)
                TODO("update the presenters locks here")
                //this.presenter.locks = result
            }
        }

        class GetLockTokenTask(private var presenter: AccessibleLocksPresenter): AsyncTask<String, Void, String>() {
            override fun doInBackground(vararg params: String?): String {
                TODO("Get locks token call here") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                TODO("callback token back to the AddlockRecylerAdapter here")
                //this.presenter.locks = result
            }
        }
    }
}
package vsec.com.slockandroid.Presenters.RentLockActivity

import android.app.Activity
import android.os.AsyncTask
import android.view.View
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.generalModels.LockRentBody

class RentLockPresenter(private val view: View) {
    interface View {
        fun toastLong(message: String)
        fun changeActivity(toActivity: Class<Activity>, extras: Map<String, String> = emptyMap())
    }

    fun assertEqual(element1: String, element2: String): Boolean = (element1 == element2)

    fun checkEmailValidity(element: String): Boolean {
        return Helpers.checkEmailIsValid(element)
    }

    companion object {
        class RentLockTask(private var presenter: RentLockPresenter):
            AsyncTask<LockRentBody, Void, String>() {
            override fun doInBackground(vararg params: LockRentBody?): String {
                if(params.isEmpty())
                    return "500"
                return ApiController.rentAlock(params[0] as LockRentBody)
            }

            override fun onPostExecute(result: String?) {
                when(result){
                    "200" ->{
                        this.presenter.view.toastLong("your lock has been shared")
                        this.presenter.view.changeActivity(HomeView::class.java as Class<Activity>)
                    }
                    else -> this.presenter.view.toastLong("something went wrong")
                }
                super.onPostExecute(result)
            }
        }
    }
}
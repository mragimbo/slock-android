package vsec.com.slockandroid.Presenters.RentLockActivity

import android.app.Activity
import android.os.AsyncTask
import android.view.View
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Controllers.LockAuthController
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.generalModels.LockRentBody
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.SimpleFormatter
import java.time.format.DateTimeFormatter.ISO_DATE_TIME



class RentLockPresenter(private val view: View) {
    private var startDate: String? = null
    private var startTime: String? = null

    private var endDate: String? = null
    private var endTime: String? = null

    private var lockRentBody: LockRentBody = LockRentBody()

    private lateinit var rentLockTask: RentLockTask

    interface View {
        fun toastLong(message: String)
        fun changeActivity(toActivity: Class<Activity>, extras: Map<String, String> = emptyMap())
    }

    fun assertEqual(element1: String, element2: String): Boolean = (element1 == element2)

    fun checkEmailValidity(element: String): Boolean {
        return Helpers.checkEmailIsValid(element)
    }

    fun checkStartDateValidity(): Boolean{
        if(this.startDate == null || this.startTime == null)
            return false
        try {
            val startDate: Date = Date.from(Instant.parse(this.startDate + "T" + this.startTime + ":00.000Z"))
            if(startDate.after(Date.from(Instant.now())))
                return true
            return false
        }catch (e: Exception){
            return false
        }
    }

    fun checkEndDateValidity(): Boolean{
        if(this.endDate == null || this.endTime == null)
            return false

        try {
            val startDate: Date = Date.from(Instant.parse(this.startDate + "T" + this.startTime + ":00.000Z"))
            val endDate: Date = Date.from(Instant.parse(this.endDate + "T" + this.endTime + ":00.000Z"))
            if(endDate.after(startDate))
                return true
            return false
        }catch (e: Exception){
            return false
        }
    }

    fun updateStartDate(date: String){
        this.startDate = date
    }

    fun updateStartTime(time: String){
        this.startTime = time
    }

    fun updateEndDate(date: String){
        this.endDate = date
    }

    fun updateEndTime(time: String){
        this.endTime = time
    }

    fun updateLockId(lockId: String){
        this.lockRentBody.setLockId(lockId)
    }

    fun updateEmail(email: String){
        this.lockRentBody.setRentee(email)
    }

    fun sendRentLockRequestToApi() {
        val startDate: Date = Date.from(Instant.parse(this.startDate + "T" + this.startTime+ ":00.000Z"))
        val endDate: Date = Date.from(Instant.parse(this.endDate + "T" + this.endTime + ":00.000Z"))
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        this.lockRentBody.setStartDate(formatter.format(startDate))
        this.lockRentBody.setEndDate(formatter.format(endDate))
        this.rentLockTask = RentLockTask(this)
        this.rentLockTask.execute(this.lockRentBody)
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
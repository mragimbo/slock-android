package vsec.com.slockandroid.Presenters.RentLockActivity

import android.app.Activity
import android.os.AsyncTask
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.Controllers.Helpers
import vsec.com.slockandroid.Presenters.HomeActivity.HomeView
import vsec.com.slockandroid.generalModels.LockRentBody
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*




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
        if(!Helpers.validateDate(startDate) || !Helpers.validateTime(startTime))
            return false

        try {
            //var now: Date = Date.from(Instant.now())
            //val dateOffset = SimpleDateFormat("Z").format(now)
            var year = this.startDate!!.split('-')[0].toInt()
            var month = this.startDate!!.split('-')[1].toInt()
            var day = this.startDate!!.split('-')[2].toInt()

            var hour = this.startTime!!.split(':')[0].toInt()
            var minute = this.startTime!!.split(':')[1].toInt()
            val startDate: Date = Date(year-1900, month-1, day+1, hour, minute)  //Date.from(Instant.parse(this.startDate + "T" + this.startTime + ":00.000Z"))
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
            //var now: Date = Date.from(Instant.now())
            ///val dateOffset = SimpleDateFormat("Z").format(now)
            var year = this.startDate!!.split('-')[0].toInt()
            var month = this.startDate!!.split('-')[1].toInt()
            var day = this.startDate!!.split('-')[2].toInt()

            var hour = this.startTime!!.split(':')[0].toInt()
            var minute = this.startTime!!.split(':')[1].toInt()
            val startDate: Date = Date(year-1900, month-1, day+1, hour, minute)

            year = this.endDate!!.split('-')[0].toInt()
            month = this.endDate!!.split('-')[1].toInt()
            day = this.endDate!!.split('-')[2].toInt()

            hour = this.endTime!!.split(':')[0].toInt()
            minute = this.endTime!!.split(':')[1].toInt()

            //val startDate: Date = Date.from(Instant.parse(this.startDate + "T" + this.startTime + ":00.000Z"))
            val endDate: Date = Date(year -1900, month-1, day+1, hour, minute)//Date.from(Instant.parse(this.endDate + "T" + this.endTime + ":00.000Z"))
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
        //var now: Date = Date.from(Instant.now())
       //val dateOffset = SimpleDateFormat("Z").format(now)

        var year = this.startDate!!.split('-')[0].toInt()
        var month = this.startDate!!.split('-')[1].toInt()
        var day = this.startDate!!.split('-')[2].toInt()

        var hour = this.startTime!!.split(':')[0].toInt()
        var minute = this.startTime!!.split(':')[1].toInt()
        val startDate: Date = Date(year - 1900, month -1, day+1, hour, minute)

        year = this.endDate!!.split('-')[0].toInt()
        month = this.endDate!!.split('-')[1].toInt()
        day = this.endDate!!.split('-')[2].toInt()

        hour = this.endTime!!.split(':')[0].toInt()
        minute = this.endTime!!.split(':')[1].toInt()

        val endDate: Date = Date(year - 1900, month-1, day +1, hour, minute)

        val tz = TimeZone.getTimeZone("UTC")
        val df =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'") // Quoted "Z" to indicate UTC, no timezone offset
        df.timeZone = tz
        val nowAsISOstartDate = df.format(startDate)
        val nowAsISOendDate = df.format(endDate)

        this.lockRentBody.setStartDate(nowAsISOstartDate)
        this.lockRentBody.setEndDate(nowAsISOendDate)
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
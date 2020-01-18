package vsec.com.slockandroid.generalModels

import android.content.Context
import vsec.com.slockandroid.Controllers.LockAuthController

interface _LocksOverviewPresenter{
    val view: View
    var lockAuthController: LockAuthController

    fun setLocks(locksJson: String)
    fun onScanDone(lock: Lock, command: String)

    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String> = HashMap())
        fun toastLong(message: String)
        fun refreshList(locks: List<Lock>)
        fun getContext(): Context?
    }
}
package vsec.com.slockandroid.generalModels

interface _LocksOverviewPresenter{
    val view: View

    fun setLocks(locksJson: String)
    fun onScanDone(lock: Lock, command: String)

    interface View {
        fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String> = HashMap())
        fun toastLong(message: String)
        fun refreshList(locks: List<Lock>)
    }
}
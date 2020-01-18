package vsec.com.slockandroid.Presenters.OwnedLocksActivity

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseList
import vsec.com.slockandroid.Controllers.LockAuthController
import vsec.com.slockandroid.generalModels.*

class OwnedLocksPresenter(override val view: _LocksOverviewPresenter.View): _LocksOverviewPresenter{
    private var lockData: List<Lock> = emptyList()

    override var lockAuthController: LockAuthController = LockAuthController(this)

    override fun onScanDone(lock: Lock, command: String){}

    @ImplicitReflectionSerializer
    override fun setLocks(locksJson: String) {
        this.lockData = Json.parseList(locksJson)
        this.view.refreshList(this.lockData)
    }

    fun fetchAccessibleLocks() {
        this.lockAuthController.executeGetLocks("/ownedlocks")
    }
}
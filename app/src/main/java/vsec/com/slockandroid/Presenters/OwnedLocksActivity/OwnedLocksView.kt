package vsec.com.slockandroid.Presenters.OwnedLocksActivity

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_accessible_locks.*

import kotlinx.android.synthetic.main.activity_owned_locks.*
import vsec.com.slockandroid.Presenters.AccessibleLocksActivity.AccessibleLocksRecyclerAdapter

import vsec.com.slockandroid.Presenters.RegisterLockActivity.RegisterLockView
import vsec.com.slockandroid.Presenters.RegisterLockActivity.RegisterLockPresenter
import vsec.com.slockandroid.R
import vsec.com.slockandroid.generalModels.Lock
import vsec.com.slockandroid.generalModels._LocksOverviewPresenter

class OwnedLocksView : Activity(), _LocksOverviewPresenter.View {
    private lateinit var presenter: OwnedLocksPresenter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owned_locks)
        presenter = OwnedLocksPresenter(this)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        btn_add_lock.setOnClickListener {
            var extras: MutableMap<String, String> = mutableMapOf()
            extras["empty_field"] = ""
            changeActivity(RegisterLockView::class.java, extras)

        }
    }

    override fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String>) {
        val intent: Intent = Intent(this, toActivity).apply {
            for (e in extras) {
                putExtra(e.key, e.value)
            }
        }
        startActivity(intent)
    }

    override fun toastLong(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun refreshList(locks: List<Lock>) {
        val accLockAdapter = OwnedLocksRecyclerAdapter(this.presenter,locks)
        recyclerView.adapter = accLockAdapter
    }
}

package vsec.com.slockandroid.Presenters.AccessibleLocksActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_accessible_locks.*
import vsec.com.slockandroid.R

class AccessibleLocksView : AppCompatActivity(), AccessibleLocksPresenter.View {

    private lateinit var presenter: AccessibleLocksPresenter;
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var presenter: AccessibleLocksPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessible_locks)
        this.presenter = AccessibleLocksPresenter(this)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        this.presenter = AccessibleLocksPresenter(this)

        val lock_data = presenter.GetAccessibleLocks()
        //TODO String to JSON
        //TODO JSON to Hashmap like below

        //Test data for recyclerView
        var locks =  HashMap<String, String>()
        locks.put("lock_1","token_1")
        locks.put("lock_2", "token_2")
        locks.put("lock_3", "token_3")

        val accLockAdapter = AccessibleLocksRecyclerAdapter(locks)
        recyclerView.adapter = accLockAdapter
    }

    override fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toastLong(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

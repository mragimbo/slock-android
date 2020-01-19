package vsec.com.slockandroid.Presenters.AccessibleLocksActivity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_accessible_locks.*
import kotlinx.android.synthetic.main.activity_accessible_locks.tv_empty_list
import vsec.com.slockandroid.R
import vsec.com.slockandroid.generalModels._LocksOverviewPresenter

class AccessibleLocksView : AppCompatActivity(), _LocksOverviewPresenter.View {
    override fun getContext(): Context? {
        return this
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var presenter: AccessibleLocksPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessible_locks)
        this.presenter = AccessibleLocksPresenter(this)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        presenter.fetchAccessibleLocks()

        //TODO String to JSON
        //TODO JSON to Hashmap like below
    }

    override fun <T> changeActivity(toActivity: Class<T>, extras: Map<String, String>) {
        val intent: Intent = Intent(this, toActivity).apply{
            for(e in extras){
                putExtra(e.key, e.value)
            }
        }
        startActivity(intent)
    }

    override fun toastLong(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun refreshList(locks: List<vsec.com.slockandroid.generalModels.Lock>) {
        this.runOnUiThread {
            val accLockAdapter = AccessibleLocksRecyclerAdapter(this.presenter,locks)
            recyclerView.adapter = accLockAdapter

            if(locks.isEmpty()){
                tv_empty_list.visibility = View.VISIBLE
                tv_empty_list.bringToFront()
            }else{
                tv_empty_list.visibility = View.INVISIBLE
            }
        }
    }
}

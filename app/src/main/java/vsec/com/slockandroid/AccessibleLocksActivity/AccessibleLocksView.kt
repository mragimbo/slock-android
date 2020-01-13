package vsec.com.slockandroid.AccessibleLocksActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_accessible_locks.*
import vsec.com.slockandroid.R

class AccessibleLocksView : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessible_locks)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val accLockAdapter = AddLocksRecyclerAdapter()

        recyclerView.adapter = accLockAdapter


    }
}

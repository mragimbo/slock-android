package vsec.com.slockandroid.AccessibleLocksActivity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import vsec.com.slockandroid.R


class AddLocksRecyclerAdapter: RecyclerView.Adapter<AddLocksRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var lockname: TextView = view.findViewById(R.id.tx_acc_lock_name)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        return ViewHolder( LayoutInflater.from(p0.context).inflate(R.layout.accessible_locks_recyclerview_item_row, p0, false))

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return 3

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.lockname.text = "lock_name_here"

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

}
}


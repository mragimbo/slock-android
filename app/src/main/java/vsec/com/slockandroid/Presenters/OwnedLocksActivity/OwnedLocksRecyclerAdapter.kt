package vsec.com.slockandroid.Presenters.OwnedLocksActivity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import vsec.com.slockandroid.Presenters.RentLockActivity.RentLockView
import vsec.com.slockandroid.R
import vsec.com.slockandroid.generalModels.Lock

class OwnedLocksRecyclerAdapter(
    private val presenter: OwnedLocksPresenter,
    private val data: List<Lock>
): RecyclerView.Adapter<OwnedLocksRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder( LayoutInflater.from(p0.context).inflate(R.layout.owned_locks_recyclerview_item_row, p0, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.lockname.text = data[p1].getName()
        //buttonListeners for opening and closing
        p0.btn_share.setOnClickListener{
            var extras = HashMap<String, String>()
            extras["lockId"] = data[p1].getId().toString()
            this.presenter.view.changeActivity(RentLockView::class.java, extras)
            this.presenter.executeCommand(data[p1],1)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var lockname: TextView = view.findViewById(R.id.tx_acc_lock_name)
        var btn_share: TextView = view.findViewById(R.id.btn_acc_lock_share)
        var toast = Toast.makeText( view.context, "", Toast.LENGTH_SHORT)
    }

}
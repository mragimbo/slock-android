package vsec.com.slockandroid.Presenters.AccessibleLocksActivity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.accessible_locks_recyclerview_item_row.view.*
import vsec.com.slockandroid.R

class AddLocksRecyclerAdapter(private val presenter: AccessibleLocksPresenter): RecyclerView.Adapter<AddLocksRecyclerAdapter.ViewHolder>() {
    private val data = presenter.getLocks()


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var lockname: TextView = view.findViewById(R.id.tx_acc_lock_name)

        var btn_open: TextView = view.findViewById(R.id.btn_acc_lock_open)
        var btn_close: TextView = view.findViewById(R.id.btn_acc_lock_close)
        var toast = Toast.makeText( view.context, "", Toast.LENGTH_SHORT)
    }



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        return ViewHolder( LayoutInflater.from(p0.context).inflate(R.layout.accessible_locks_recyclerview_item_row, p0, false))
    }

    override fun getItemCount(): Int {
        return data.keys.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.lockname.text = data.keys.elementAt(p1)


        //buttonListeners for opening and closing
        p0.btn_open.setOnClickListener(){
            this.presenter.executeLockCommand(1)
            p0.toast.setText("Open: "+data.get(data.keys.elementAt(p1)))
            p0.toast.show()
        }

        p0.btn_close.setOnClickListener(){
            this.presenter.executeLockCommand(-1)
            p0.toast.setText("Close: "+data.get(data.keys.elementAt(p1)))
            p0.toast.show()
        }

}
}


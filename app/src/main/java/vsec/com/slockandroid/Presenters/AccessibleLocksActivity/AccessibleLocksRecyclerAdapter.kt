package vsec.com.slockandroid.Presenters.AccessibleLocksActivity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import vsec.com.slockandroid.R
import vsec.com.slockandroid.generalModels.ViewHolder
import vsec.com.slockandroid.generalModels.*

class AccessibleLocksRecyclerAdapter(
    private val presenter: AccessibleLocksPresenter,
    private val data: List<Lock>
): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder( LayoutInflater.from(p0.context).inflate(R.layout.accessible_locks_recyclerview_item_row, p0, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.lockname.text = data[p1].getName()

        //buttonListeners for opening and closing
        p0.btn_open.setOnClickListener{
            p0.toast.setText("Open: "+ data[p1].getName())
            p0.toast.show()
            this.presenter.executeCommand(data[p1],1)
        }

        p0.btn_close.setOnClickListener{
            p0.toast.setText("Close: "+ data[p1].getName())
            p0.toast.show()
            this.presenter.executeCommand(data[p1], -1)
        }
    }
}


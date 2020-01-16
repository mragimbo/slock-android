package vsec.com.slockandroid.generalModels

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import vsec.com.slockandroid.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var lockname: TextView = view.findViewById(R.id.tx_acc_lock_name)

    var btn_open: TextView = view.findViewById(R.id.btn_acc_lock_open)
    var btn_close: TextView = view.findViewById(R.id.btn_acc_lock_close)
    var toast = Toast.makeText( view.context, "", Toast.LENGTH_SHORT)
}

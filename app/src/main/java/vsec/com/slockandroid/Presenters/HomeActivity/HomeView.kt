package vsec.com.slockandroid.Presenters.HomeActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import vsec.com.slockandroid.Presenters.OwnedLocksActivity.OwnedLocksActivity
import vsec.com.slockandroid.Presenters.RegisterActivity.RegisterPresenter
import vsec.com.slockandroid.Presenters.SettingsActivity.SettingsView
import vsec.com.slockandroid.R

class HomeView : Activity(), HomePresenter.View  {

    private lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        this.presenter = HomePresenter(this)

        btn_own_locks.setOnClickListener{
            var extras: MutableMap<String, String> = mutableMapOf()
            extras.put("empty_field", "")
            this.changeActivity(OwnedLocksView::class.java as Class<Activity>,extras)
        }

        btn_settings.setOnClickListener{
            var extras: MutableMap<String, String> = mutableMapOf()
            extras.put("empty_field", "")
            this.changeActivity(SettingsView::class.java as Class<Activity>,extras)
        }

    }


    override fun changeActivity(toActivity: Class<Activity>, extra: Map<String, String>) {
        val intent: Intent = Intent(this, toActivity).apply{
            for(e in extra){
                putExtra(e.key, e.value)
            }
        }
        startActivity(intent)
    }
}

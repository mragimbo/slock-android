package vsec.com.slockandroid.Presenters.HomeActivity

import android.app.Activity
import android.os.Bundle
import vsec.com.slockandroid.R

class HomeView : Activity(), HomePresenter.View  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}

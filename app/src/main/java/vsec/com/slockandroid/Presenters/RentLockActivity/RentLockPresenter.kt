package vsec.com.slockandroid.Presenters.RentLockActivity

import android.view.View
import vsec.com.slockandroid.Controllers.Helpers

class RentLockPresenter(private val view: View) {
    interface View {}

    //TODO ASYNC stuff and API calls

    fun assertEqual(element1: String, element2: String): Boolean = (element1 == element2)

    fun checkEmailValidity(element: String): Boolean {
        return Helpers.checkEmailIsValid(element)
    }
}
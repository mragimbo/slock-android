package vsec.com.slockandroid.Controllers.Tasks

import android.os.AsyncTask
import vsec.com.slockandroid.Controllers.ApiController
import vsec.com.slockandroid.generalModels.User

class LoginTask : AsyncTask<User, Void, String>() {
    override fun doInBackground(vararg params: User?): String? {
        return ApiController.loginUser(params[0] as User)
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        // ...
    }

}
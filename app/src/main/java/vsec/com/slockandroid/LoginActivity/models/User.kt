package vsec.com.slockandroid.LoginActivity.models

class User {
    private var email: String =  ""
    private var passwordSHA512: String = ""

    fun setEmail(email: String){
        this.email = email
    }

    fun setHashedPassword(password: String){
        this.passwordSHA512 = password;
    }
}
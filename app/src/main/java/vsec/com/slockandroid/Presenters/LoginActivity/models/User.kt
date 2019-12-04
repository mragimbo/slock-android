package vsec.com.slockandroid.Presenters.LoginActivity.models

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify

@ImplicitReflectionSerializer
class User {
    private var email: String =  ""
    private var passwordSHA512: String = ""
    val salt: String = "ad18cbd8676391bf70b196e6df30065b"


    fun setEmail(email: String){
        this.email = email
    }

    fun setHashedPassword(password: String){
        this.passwordSHA512 = password;
    }

    fun toJSON(): String{
        return Json.stringify(this);
    }

    fun getPassHash(): String{
        return this.passwordSHA512;
    }
}
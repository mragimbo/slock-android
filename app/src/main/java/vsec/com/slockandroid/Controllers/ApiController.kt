package vsec.com.slockandroid.Controllers

import android.service.voice.AlwaysOnHotwordDetector
import android.util.Log
import vsec.com.slockandroid.generalModels.ChangePasswordModel
import vsec.com.slockandroid.generalModels.Lock
import vsec.com.slockandroid.generalModels.User
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

object ApiController {
    private val apiDomain: String =  "slock.wtf"
    private val apiPort: Int = 443//54319
    private var sessionToken: String = ""

    fun LogoutUser(): String {
        val url = URL("https://" + this.apiDomain + ":" + this.apiPort + "/v1/logout")

        with(url.openConnection() as HttpsURLConnection) {
            sslSocketFactory = KeyStoreController.sslContext.socketFactory
            requestMethod = "GET"

            setRequestProperty("charset", "utf-8")
            setRequestProperty("token", ApiController.sessionToken )

            if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_CREATED) {
                ApiController.sessionToken = ""
            }
            return responseCode.toString()
        }
    }

    fun loginUser(user: User): String {
        val url = URL("https://" + this.apiDomain + ":" + this.apiPort + "/v1/login")

        with(url.openConnection() as HttpsURLConnection) {
            sslSocketFactory = KeyStoreController.sslContext.socketFactory
            requestMethod = "POST"

            val postData: ByteArray = user.toJSON().toByteArray(StandardCharsets.UTF_8)
            setRequestProperty("charset", "utf-8")
            setRequestProperty("content-length", postData.size.toString())
            setRequestProperty("Content-Type", "application/json")
            try{
                val outputStream: DataOutputStream = DataOutputStream(outputStream)
                outputStream.write(postData)
                outputStream.flush()
            }catch (exeption: Exception){
                exeption.printStackTrace()
            }

            if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_CREATED) {
                try {
                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val sessionToken: String = reader.readLine()
                    ApiController.sessionToken = sessionToken
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
            return responseCode.toString()
        }
    }

    fun registerUser(user: User): String {
        val url = URL("https://" + this.apiDomain + ":" + this.apiPort + "/v1/register")

        with(url.openConnection() as HttpsURLConnection) {
            sslSocketFactory = KeyStoreController.sslContext.socketFactory
            requestMethod = "POST"

            val postData: ByteArray = user.toJSON().toByteArray(StandardCharsets.UTF_8)
            setRequestProperty("charset", "utf-8")
            setRequestProperty("content-length", postData.size.toString())
            setRequestProperty("Content-Type", "application/json")
            try{
                val outputStream: DataOutputStream = DataOutputStream(outputStream)
                outputStream.write(postData)
                outputStream.flush()
            }catch (exeption: Exception){
                exeption.printStackTrace()
            }

            if (responseCode != HttpsURLConnection.HTTP_OK && responseCode != HttpsURLConnection.HTTP_CREATED) {
                try {
                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val output: String = reader.readLine()

                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
            return responseCode.toString()
        }
    }

    fun registerLock(lock: Lock): String {
        val url = URL("https://" + this.apiDomain + ":" + this.apiPort + "/v1/locks/activate")

        with(url.openConnection() as HttpsURLConnection) {
            sslSocketFactory = KeyStoreController.sslContext.socketFactory
            requestMethod = "POST"

            val postData: ByteArray = lock.toJSON().toByteArray(StandardCharsets.UTF_8)
            setRequestProperty("charset", "utf-8")
            setRequestProperty("content-length", postData.size.toString())
            setRequestProperty("Content-Type", "application/json")
            try{
                val outputStream: DataOutputStream = DataOutputStream(outputStream)
                outputStream.write(postData)
                outputStream.flush()
            }catch (exeption: Exception){
                exeption.printStackTrace()
            }

            if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_CREATED) {
                try {
                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val sessionToken: String = reader.readLine()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
            return responseCode.toString()
        }
    }

    fun ChangeDetails(changeObject: ChangePasswordModel): String? {
        val url = URL("https://" + this.apiDomain + ":" + this.apiPort + "/v1/changeDetails")
        val postData: ByteArray = changeObject.toJSON().toByteArray(StandardCharsets.UTF_8)

        with(url.openConnection() as HttpsURLConnection) {
            sslSocketFactory = KeyStoreController.sslContext.socketFactory
            requestMethod = "GET"

            setRequestProperty("charset", "utf-8")
            setRequestProperty("content-length", postData.size.toString())
            setRequestProperty("token", ApiController.sessionToken )

            try{
                val outputStream: DataOutputStream = DataOutputStream(outputStream)
                outputStream.write(postData)
                outputStream.flush()
            }catch (exception: Exception){

            }
            return responseCode.toString()
        }
    }
}
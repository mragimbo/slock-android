package vsec.com.slockandroid.Controllers

import android.util.Log
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

    fun LogoutUser(session: String): String {
        return "200"
        val url = URL("https://" + this.apiDomain + ":" + this.apiPort + "/v1/logout")

        with(url.openConnection() as HttpsURLConnection) {
            sslSocketFactory = KeyStoreController.sslContext.socketFactory
            requestMethod = "GET"

            setRequestProperty("charset", "utf-8")
            setRequestProperty("Session-Token", session)

            if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_CREATED) {
                try {
                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val output: String = reader.readLine()
                    return "200"
                } catch (exception: Exception) {
                    throw Exception("Exception while push the reading package  $exception.message")
                }
            }
            return responseCode.toString()
        }



    }

    fun loginUser(user: User): String {
        return "200"
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

            }

            if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_CREATED) {
                try {
                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val output: String = reader.readLine()
                    return "200"
                } catch (exception: Exception) {
                    throw Exception("Exception while push the reading package  $exception.message")
                }
            }
            return responseCode.toString()
        }
    }

    fun registerUser(user: User): Boolean {
        return true;
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
                val e = exeption
            }

            try {
                val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                val output: String = reader.readLine()
                Log.e("error: ", output)
            }catch (e: Exception){
                var s = e
            }
            if (responseCode != HttpsURLConnection.HTTP_OK && responseCode != HttpsURLConnection.HTTP_CREATED) {
                try {
                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val output: String = reader.readLine()

                    println("There was error while connecting the chat $output")
                    System.exit(0)

                } catch (exception: Exception) {
                    throw Exception("Exception while push the notification  $exception.message")
                }
            }
        }
        //return false
    }

    fun registerLock(lock: Lock): String{
        return "200"
        val url = URL("https://" + this.apiDomain + ":" + this.apiPort + "/v1/locks/register")

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

            }

            if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_CREATED) {
                try {
                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val output: String = reader.readLine()
                    return "200"
                } catch (exception: Exception) {
                    throw Exception("Exception while push the reading package  $exception.message")
                }
            }
            return responseCode.toString()
        }
    }
}
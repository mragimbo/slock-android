package vsec.com.slockandroid.Controllers

import kotlinx.serialization.ImplicitReflectionSerializer
import vsec.com.slockandroid.Presenters.LoginActivity.models.User
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

object ApiController {
    private val apiDomain: String =  "127.0.0.1"
    private val apiPort: Int = 8080

    fun loginUser(user: User): Boolean {
        return true;

        val url = URL("https://" + this.apiDomain + ":" + this.apiPort + "/v1/login");

        with(url.openConnection() as HttpsURLConnection) {
            requestMethod = "POST"

            val postData: ByteArray = user.toJSON().toByteArray(StandardCharsets.UTF_8)
            setRequestProperty("charset", "utf-8")
            setRequestProperty("content-lenght", postData.size.toString())
            setRequestProperty("Content-Type", "application/json")
            try{
                val outputStream: DataOutputStream = DataOutputStream(outputStream)
                outputStream.write(postData)
                outputStream.flush()
            }catch (exeption: Exception){

            }

            if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_CREATED) {
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
    }
}
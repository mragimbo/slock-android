package vsec.com.slockandroid.generalModels

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
class RatchetSyncBody(
    private var token: String,
    private var counter: String
){
    fun toJSON(): String{
        return Json.stringify(serializer(),this)
    }
}
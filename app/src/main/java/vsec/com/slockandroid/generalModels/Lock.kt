package vsec.com.slockandroid.generalModels

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@Serializable
class Lock {
    private var bleUuid: String? = null
    private var secret: String? = null
    private var name: String? = null
    private var discription: String? = null

    @Serializer(forClass = User::class)
    companion object: KSerializer<Lock> {
        override fun serialize(output: Encoder, obj: Lock) {
            val elemOutput = output.beginStructure(descriptor)
            if (obj.bleUuid != null) elemOutput.encodeStringElement(descriptor, 0, obj.bleUuid as String)
            if (obj.secret != null) elemOutput.encodeStringElement(descriptor, 1, obj.secret as String)
            if (obj.name != null) elemOutput.encodeStringElement(descriptor, 2, obj.name as String)
            if (obj.discription != null) elemOutput.encodeStringElement(descriptor, 2, obj.discription as String)

            elemOutput.endStructure(descriptor)
        }
    }
    fun setUuid(uuid: String){
        this.bleUuid = "SLOCK_$uuid".substring(0,26)
    }

    fun getUuid(): String?{
        return this.bleUuid
    }

    fun setSecret(secret: String){
        this.secret = secret
    }

    fun getSecret(): String?{
        return this.secret
    }

    fun setName(name: String){
        this.name = name
    }

    fun setDiscription(coutry: String, city: String, street: String, streetNumber: String){
        this.discription = "coutry: " + coutry +
                "\nity: " + city +
                "\nstreet: " + street +
                "\nstreetnumber: " + streetNumber
    }

    fun clear(){
        this.bleUuid = null
        this.secret = null
    }

    fun toJSON(): String {
        return Json.stringify(serializer(), this)
    }
}
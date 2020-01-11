package vsec.com.slockandroid.generalModels

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlin.jvm.Transient

@Serializable
class Lock {
    private var bleUuid: String? = null
    @Transient private var uuid: String? = null
    private var secret: String? = null
    private var name: String? = null
    private var discription: String? = null
    private var productKey: String? = null

    @Serializer(forClass = Lock::class)
    companion object: KSerializer<Lock> {
        override fun serialize(output: Encoder, obj: Lock) {
            val elemOutput = output.beginStructure(descriptor)
            if (obj.bleUuid != null) elemOutput.encodeStringElement(descriptor, 0, obj.bleUuid as String)
            if (obj.uuid != null) elemOutput.encodeStringElement(descriptor, 1, obj.uuid as String)
            if (obj.secret != null) elemOutput.encodeStringElement(descriptor, 2, obj.secret as String)
            if (obj.name != null) elemOutput.encodeStringElement(descriptor, 3, obj.name as String)
            if (obj.discription != null) elemOutput.encodeStringElement(descriptor, 4, obj.discription as String)
            if (obj.productKey != null) elemOutput.encodeStringElement(descriptor, 5, obj.productKey as String)

            elemOutput.endStructure(descriptor)
        }
    }
    fun setUuid(uuid: String){
        this.bleUuid = "SLOCK_$uuid".substring(0,26)
    }

    fun getUuid(): String?{
        return this.bleUuid
    }

    fun setProductKey(key: String){
        this.productKey = productKey
    }

    fun setBleAddress(address: String){
        this.bleUuid = address
    }

    fun getBleAddress(): String?{
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

    fun setDiscription(description: String){
        this.discription = description
    }

    fun clear(){
        this.bleUuid = null
        this.secret = null
    }

    fun toJSON(): String {
        return Json.stringify(serializer(), this)
    }
}
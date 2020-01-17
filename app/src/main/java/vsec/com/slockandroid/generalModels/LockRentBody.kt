package vsec.com.slockandroid.generalModels

import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json

@Serializable
class LockRentBody{
    @Transient private var lockId: Int? = null
    private var username: String? = null
    private var startDate: String? = null
    private var endDate: String? = null

    @Serializer(forClass = LockRentBody::class)
    companion object: KSerializer<LockRentBody> {
        override fun serialize(output: Encoder, obj: LockRentBody) {
            val elemOutput = output.beginStructure(descriptor)
            if (obj.username != null) elemOutput.encodeStringElement(descriptor, 0, obj.username as String)
            if (obj.startDate != null) elemOutput.encodeStringElement(descriptor, 1, obj.startDate as String)
            if (obj.endDate != null) elemOutput.encodeStringElement(descriptor, 2, obj.endDate as String)

            elemOutput.endStructure(descriptor)
        }
    }

    fun setLockId(id: Int){
        this.lockId = id
    }

    fun setRentee(email: String){
        this.username = email
    }

    fun setStartDate(date: String){
        this.startDate = date
    }

    fun setEndDate(date: String){
        this.endDate = date
    }

    fun toJSON(): String{
        return Json.stringify(serializer(),this)
    }

    fun getId(): Int? {
        return this.lockId
    }

}
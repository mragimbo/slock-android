package vsec.com.slockandroid.generalModels

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@Serializable
class ChangePasswordModel {
    private var oldPassword: String? = null
    private var newPassword: String? = null

    @Serializer(forClass = ChangePasswordModel::class)
    companion object: KSerializer<ChangePasswordModel> {
        override fun serialize(output: Encoder, obj: ChangePasswordModel) {
            val elemOutput = output.beginStructure(descriptor)
            if (obj.oldPassword != null) elemOutput.encodeStringElement(descriptor, 0, obj.oldPassword as String)
            if (obj.newPassword != null) elemOutput.encodeStringElement(descriptor, 1, obj.newPassword as String)
            elemOutput.endStructure(descriptor)
        }
    }

    fun setOldHashedPassword(oldPassword: String){
        this.oldPassword = oldPassword
    }

    fun setNewHashedPassword(newPassword: String){
        this.newPassword = newPassword
    }

    fun clear(){
        this.oldPassword = null
        this.newPassword = null
    }

    fun toJSON(): String {
        var json = Json.stringify(serializer(), this)
        return json
    }
}
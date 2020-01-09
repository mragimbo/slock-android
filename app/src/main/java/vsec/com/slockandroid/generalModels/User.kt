package vsec.com.slockandroid.generalModels

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@Serializable
class User {
    @Transient val salt: String = "ad18cbd8676391bf70b196e6df30065b"
    private var username: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? =  null
    private var password: String? = null

    @Serializer(forClass = User::class)
    companion object: KSerializer<User> {
        override fun serialize(output: Encoder, obj: User) {
            val elemOutput = output.beginStructure(descriptor)
            if (obj.username != null) elemOutput.encodeStringElement(descriptor, 0, obj.username as String)
            if (obj.firstName != null) elemOutput.encodeStringElement(descriptor, 1, obj.firstName as String)
            if (obj.lastName != null) elemOutput.encodeStringElement(descriptor, 2, obj.lastName as String)
            if (obj.email != null) elemOutput.encodeStringElement(descriptor, 3, obj.email as String)
            if (obj.password != null) elemOutput.encodeStringElement(descriptor, 4, obj.password as String)
            elemOutput.endStructure(descriptor)
        }
    }



    fun setUsername(username: String){
        this.username = username
    }

    fun setFirstName(firstName: String){
        this.firstName = firstName
    }

    fun setLastName(lastName: String){
        this.lastName = lastName
    }

    fun setEmail(email: String){
        this.email = email
    }

    fun setHashedPassword(password: String){
        this.password = password
    }

    fun clear(){
        this.username = null
        this.firstName = null
        this.lastName = null
        this.email = null
        this.password = null
    }

    fun toJSON(): String {
        var json = Json.stringify(serializer(), this)
        return json
    }
}
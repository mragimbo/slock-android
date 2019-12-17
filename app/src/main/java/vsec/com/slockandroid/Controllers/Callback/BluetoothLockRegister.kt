package vsec.com.slockandroid.Controllers.Callback

import android.bluetooth.*
import android.content.ContentValues
import android.util.Log
import vsec.com.slockandroid.Controllers.Helpers
import java.util.*
import kotlin.collections.ArrayList

private const val SERVICE_REGISTER_ID = "7c3e0e35-996f-4745-a62f-ecb0d6e971b2"
private const val CHARACTERISTIC_REGISTER_NAME = "c3465381-d3fe-4234-bd2b-a642eaedb1fe"
private const val CHARACTERISTIC_REGISTER_SECRET = "1f894374-6b00-4c13-9782-bfa63a479ed6"


class BluetoothLockRegister(private val lockUuid: String, private val lockTokenSeed: String): BluetoothGattCallback() {

    override fun onConnectionStateChange(
        gatt: BluetoothGatt,
        status: Int,
        newState: Int
    ) {
        when (newState) {
            BluetoothProfile.STATE_CONNECTED -> {
                gatt.beginReliableWrite()
                gatt.discoverServices()
            }
            BluetoothProfile.STATE_DISCONNECTED -> {
                Log.i(ContentValues.TAG, "Disconnected from GATT server.")
            }
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        Log.e("on services discoverd", status.toString())
        if (status == BluetoothGatt.GATT_SUCCESS) {
            sendNameCharacteristic(gatt)
        }
    }

    private var characteristics: ArrayList<BluetoothGattCharacteristic?> = ArrayList()
    override fun onCharacteristicWrite(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic?,
        status: Int
    ) {
        characteristics.add(characteristic)
        val hasName: Boolean = characteristics.find { it?.uuid == UUID.fromString(CHARACTERISTIC_REGISTER_NAME) } != null
        val hasSecret: Boolean = characteristics.find { it?.uuid == UUID.fromString(CHARACTERISTIC_REGISTER_SECRET) } != null

        if(hasName && hasSecret){
            gatt?.executeReliableWrite()
        }else if(hasName) {
            sendSecretCharacteristic(gatt)
        }else if(hasSecret){
            sendNameCharacteristic(gatt)
        }
    }

    fun sendNameCharacteristic(gatt: BluetoothGatt?){
        if(gatt == null)
            return

        val registerservice: BluetoothGattService = gatt.getService(UUID.fromString(SERVICE_REGISTER_ID))

        val registerNameCharacteristic: BluetoothGattCharacteristic = registerservice.getCharacteristic(UUID.fromString(
            CHARACTERISTIC_REGISTER_NAME))
        registerNameCharacteristic.setValue(this.lockUuid)

        gatt.writeCharacteristic(registerNameCharacteristic)
    }

    fun sendSecretCharacteristic(gatt: BluetoothGatt?){
        if(gatt == null)
            return

        val registerservice: BluetoothGattService = gatt.getService(UUID.fromString(SERVICE_REGISTER_ID))

        val registerSecretCharacteristic: BluetoothGattCharacteristic = registerservice.getCharacteristic(UUID.fromString(CHARACTERISTIC_REGISTER_SECRET))
        registerSecretCharacteristic.setValue(this.lockTokenSeed)
        gatt.writeCharacteristic(registerSecretCharacteristic)
    }
}
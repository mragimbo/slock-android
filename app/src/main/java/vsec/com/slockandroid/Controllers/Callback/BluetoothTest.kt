import android.bluetooth.*
import android.content.ContentValues
import android.util.Log
import vsec.com.slockandroid.generalModels.Lock
import java.util.*
import kotlin.collections.ArrayList

class BluetoothTest( private val done: () -> Unit): BluetoothGattCallback() {

    override fun onConnectionStateChange(
        gatt: BluetoothGatt,
        status: Int,
        newState: Int
    ) {
        when (newState) {
            BluetoothProfile.STATE_CONNECTED -> {
                gatt.requestMtu(128)
                Log.i(ContentValues.TAG, "connected from GATT server.")

            }
            BluetoothProfile.STATE_DISCONNECTED -> {
                Log.i(ContentValues.TAG, "Disconnected from GATT server.")
            }
        }
    }

    override fun onMtuChanged(gatt: BluetoothGatt?, mtu: Int, status: Int) {
        super.onMtuChanged(gatt, mtu, status)
        if(mtu >= 128) {
            gatt?.beginReliableWrite()
            gatt?.discoverServices()
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        Log.e("on services discoverd", status.toString())
        if (status == BluetoothGatt.GATT_SUCCESS) {
            sendNameCharacteristic(gatt)
        }
    }

    fun sendNameCharacteristic(gatt: BluetoothGatt?){
        if(gatt == null)
            return

        val registerservice: BluetoothGattService = gatt.getService(
            UUID.fromString(
                //"7c3e0e35-996f-4745-a62f-ecb0d6e971b2"
                "ec01579e-4928-48ee-bed0-e68237efa95d"
            ))

        val registerNameCharacteristic: BluetoothGattCharacteristic = registerservice.getCharacteristic(
            UUID.fromString(
                //"c3465381-d3fe-4234-bd2b-a642eaedb1fe"
                "b3d709fe-de1c-46db-9b13-a39aac60de42"
            ))
        registerNameCharacteristic.setValue("test")

        gatt.writeCharacteristic(registerNameCharacteristic)

    }

    override fun onCharacteristicWrite(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic?,
        status: Int
    ) {
        super.onCharacteristicWrite(gatt, characteristic, status)
        gatt?.executeReliableWrite()
    }
}
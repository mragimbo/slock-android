import android.app.Notification
import android.bluetooth.*
import android.content.ContentValues
import android.util.Log
import java.util.*
import android.bluetooth.BluetoothGattDescriptor
import vsec.com.slockandroid.generalModels.Lock


class BluetoothCommandCallback(private val lock: Lock, private val command: String, private val onNotification: (lock: Lock, msg: String) -> Unit): BluetoothGattCallback() {

    private var blueActive: Boolean = false
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
                onNotification(lock,"-1")
                Log.i(ContentValues.TAG, "Disconnected from GATT server.")
            }
        }
    }

    override fun onMtuChanged(gatt: BluetoothGatt?, mtu: Int, status: Int) {
        super.onMtuChanged(gatt, mtu, status)
        if(mtu >= 128 && !this.blueActive) {
            this.blueActive = true
            gatt?.beginReliableWrite()
            gatt?.discoverServices()
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        Log.e("on services discoverd", status.toString())
        if (status == BluetoothGatt.GATT_SUCCESS) {
            //sendAuthCharacteristic(gatt)
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


            gatt?.setCharacteristicNotification(registerNameCharacteristic, true)
            val uuid = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
            val descriptor = registerNameCharacteristic?.getDescriptor(uuid)
            descriptor?.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
            gatt?.writeDescriptor(descriptor)
            //this.onDescriptorWrite(gatt,descriptor,0)*/
        }
    }

    override fun onDescriptorWrite(
        gatt: BluetoothGatt?,
        descriptor: BluetoothGattDescriptor?,
        status: Int
    ) {
        super.onDescriptorWrite(gatt, descriptor, status)
        sendAuthCharacteristic(gatt)
    }

    fun sendAuthCharacteristic(gatt: BluetoothGatt?){
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
        gatt?.setCharacteristicNotification(registerNameCharacteristic, true)

        registerNameCharacteristic.setValue(this.command)//"eDHvQAzF9R6uPTR/gUBiLj0x5JoTho1r6rukGhB7SDS9IQi/XQ4vlb4GoKdNsF4nzgw4vaw1Tam5rM5T3GSM6A==;1")

        gatt.writeCharacteristic(registerNameCharacteristic)

    }

    override fun onCharacteristicWrite(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic?,
        status: Int
    ) {
        gatt?.executeReliableWrite()
    }

    override fun onCharacteristicChanged(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic?
    ) {
        if(characteristic != null){
            var msg = String(characteristic.value)
            this.onNotification(this.lock,msg)
        }
        gatt?.disconnect()
    }
}
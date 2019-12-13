package vsec.com.slockandroid.Controllers.Callback

import android.bluetooth.*
import android.content.ContentValues
import android.util.Log
import java.util.*


object BluetoothGattConnectCallback: BluetoothGattCallback() {
    override fun onConnectionStateChange(
        gatt: BluetoothGatt,
        status: Int,
        newState: Int
    ) {
        when (newState) {
            BluetoothProfile.STATE_CONNECTED -> {
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
            val service1: BluetoothGattService = gatt.getService(UUID.fromString("7c3e0e35-996f-4745-a62f-ecb0d6e971b2"))

            var mWriteCharacteristic: BluetoothGattCharacteristic =
                service1.getCharacteristic(UUID.fromString("c3465381-d3fe-4234-bd2b-a642eaedb1fe"))
                mWriteCharacteristic.setValue("you should not see this, uuid")

            if(gatt.writeCharacteristic(mWriteCharacteristic) == false){
               Log.w("error", "Failed to write characteristic")
            }

            val service2 = gatt.getService(UUID.fromString("7c3e0e35-996f-4745-a62f-ecb0d6e971b2"))

            var mWriteCharacteristic2 = service2.getCharacteristic(UUID.fromString("1f894374-6b00-4c13-9782-bfa63a479ed6"))
            mWriteCharacteristic2.setValue("Lars you should see this, secret")
            if(gatt.writeCharacteristic(mWriteCharacteristic2) == false){
                Log.w("error2", "Hey, Lars you should not see this")
            }
        }
    }
}
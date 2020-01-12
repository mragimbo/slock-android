package vsec.com.slockandroid.Controllers.Callback

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.content.ContentValues
import android.util.Log
import java.util.*

class BluetootLockValidate(private val validationDone: (validated: Boolean) -> Unit): BluetoothGattCallback() {

    override fun onConnectionStateChange(
        gatt: BluetoothGatt,
        status: Int,
        newState: Int
    ) {
        when (newState) {
            BluetoothProfile.STATE_CONNECTED -> {
                Log.i(ContentValues.TAG, "connected from GATT server.")
                gatt.discoverServices()
            }
            BluetoothProfile.STATE_DISCONNECTED -> {
                Log.i(ContentValues.TAG, "Disconnected from GATT server.")
            }
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        super.onServicesDiscovered(gatt, status)
        if(status == 0 && gatt?.services?.find { it.uuid == UUID.fromString("ec01579e-4928-48ee-bed0-e68237efa95d") } != null){
            validationDone(true)
        }else{
            validationDone(false)
        }
    }
}
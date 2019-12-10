package vsec.com.slockandroid.Controllers.Callback

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.content.ContentValues
import android.util.Log


object BluetoothGattConnectCallback: BluetoothGattCallback() {
    override fun onConnectionStateChange(
        gatt: BluetoothGatt,
        status: Int,
        newState: Int
    ) {
        when (newState) {
            BluetoothProfile.STATE_CONNECTED -> {
                Log.i(ContentValues.TAG, "Connected from GATT server.")
            }
            BluetoothProfile.STATE_DISCONNECTED -> {
                Log.i(ContentValues.TAG, "Disconnected from GATT server.")
            }
        }
    }
}
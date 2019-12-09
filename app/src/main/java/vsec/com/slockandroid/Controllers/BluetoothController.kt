package vsec.com.slockandroid.Controllers

import android.bluetooth.*
import android.bluetooth.BluetoothAdapter.STATE_CONNECTED
import android.bluetooth.BluetoothAdapter.STATE_DISCONNECTED
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Handler
import android.util.Log


private const val SCAN_PERIOD: Long = 10000
const val ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED"
const val ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED"

object BluetoothController {
    private var myBluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothScanner: BluetoothLeScanner? = null
    private var mScanning: Boolean = false
    private var handler: Handler = Handler()
    private lateinit var context: Context

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    Log.i(TAG, "Connected from GATT server.")
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    Log.i(TAG, "Disconnected from GATT server.")
                }
            }
        }
    }

    private val bleScanner = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?)
        {
            if(result?.device?.name?.contains("SLOCK") == true) {
                var bluetoothGatt = result?.device?.connectGatt(context, false, gattCallback)
            }
            Log.d("ScanDeviceActivity", "onScanResult(): ${result?.device?.address} - ${result?.device?.name}")
        }
    }

    private val BluetoothAdapter.isDisabled: Boolean
        get() = !isEnabled

    fun initBleAdapter(context: Context): Boolean{
        this.context = context
        val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE){
            val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothManager.adapter
        }

        this.myBluetoothAdapter = bluetoothAdapter;
        this.bluetoothScanner = this.myBluetoothAdapter?.bluetoothLeScanner
        Log.d("ScanDeviceActivity", "scann start")
        return this.myBluetoothAdapter?.isDisabled == false
    }

    fun scanLeDevice(enable: Boolean) {
        if(this.bluetoothScanner == null){
            // no scanner found
            return
        }
        when (enable) {
            true -> {
                // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    mScanning = false
                    bluetoothScanner?.stopScan(bleScanner)
                }, SCAN_PERIOD)
                mScanning = true
                bluetoothScanner?.startScan(bleScanner)
            }
            else -> {
                mScanning = false
                bluetoothScanner?.stopScan(bleScanner)
            }
        }
    }
}
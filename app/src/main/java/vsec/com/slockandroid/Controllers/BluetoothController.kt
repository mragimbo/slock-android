package vsec.com.slockandroid.Controllers

import android.bluetooth.*
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.os.Handler
import android.util.Log
import vsec.com.slockandroid.Controllers.Callback.BluetoothScanCallback


//private const val SCAN_PERIOD: Long = 10000
//const val ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED"
//const val ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED"
private const val SCAN_PERIOD: Long = 10000

object BluetoothController {

    private var myBluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothScanner: BluetoothLeScanner? = null
    private var mScanning: Boolean = false
    private var handler: Handler = Handler()
    lateinit var context: Context

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
        return this.myBluetoothAdapter?.isDisabled == false
    }


    fun scanLeDevice(enable: Boolean) {
        if(this.bluetoothScanner == null){
            // no scanner found
            return
        }
        Log.e("Callback", "pre callback")

        when (enable) {
            true -> {
                BluetoothScanCallback.clearScannedDevices()
                // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    mScanning = false
                    bluetoothScanner?.stopScan(BluetoothScanCallback)
                    bluetoothScanner?.flushPendingScanResults(BluetoothScanCallback)
                    BluetoothScanCallback.connectGatt("SLOCK-ALPHA-v1")
                }, SCAN_PERIOD)
                mScanning = true
                bluetoothScanner?.startScan(BluetoothScanCallback)
            }
            else -> {
                mScanning = false
                bluetoothScanner?.stopScan(BluetoothScanCallback)
            }
        }
    }
}
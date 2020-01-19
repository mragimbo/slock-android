package vsec.com.slockandroid.Controllers

import android.bluetooth.*
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.os.Handler
import vsec.com.slockandroid.Controllers.Callback.BluetoothScanCallback

private const val SCAN_PERIOD: Long = 5000

object BluetoothController {

    private var myBluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothScanner: BluetoothLeScanner? = null
    private var mScanning: Boolean = false
    private var handler: Handler = Handler()

    private val BluetoothAdapter.isDisabled: Boolean
        get() = !isEnabled


    fun initBleAdapter(context: Context): Boolean {
        val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE){
            val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothManager.adapter
        }

        this.myBluetoothAdapter = bluetoothAdapter;
        this.bluetoothScanner = this.myBluetoothAdapter?.bluetoothLeScanner
        return this.myBluetoothAdapter?.isDisabled == false
    }


    fun scanLeDevice(enable: Boolean, onScanDone: () -> Unit) {
        if(this.bluetoothScanner == null){
            // no scanner found
            return
        }

        when (enable) {
            true -> {
                BluetoothScanCallback.clearScannedDevices()
                // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    mScanning = false
                    bluetoothScanner?.stopScan(BluetoothScanCallback)
                    bluetoothScanner?.flushPendingScanResults(BluetoothScanCallback)
                    onScanDone()
                }, SCAN_PERIOD)
                mScanning = true
                bluetoothScanner?.startScan(BluetoothScanCallback)
            }
            else -> {
                mScanning = false
                bluetoothScanner?.flushPendingScanResults(BluetoothScanCallback)
                bluetoothScanner?.stopScan(BluetoothScanCallback)
            }
        }
    }
}
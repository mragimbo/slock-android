package vsec.com.slockandroid.Controllers

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.content.Intent
import android.os.Handler


private const val SCAN_PERIOD: Long = 10000

object BluetoothController {
    private var myBluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothScanner: BluetoothLeScanner? = null
    private var mScanning: Boolean = false
    private var handler: Handler = Handler()



        private val BluetoothAdapter.isDisabled: Boolean
        get() = !isEnabled

    fun initBleAdapter(context: Context): Boolean{
        val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE){
            val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothManager.adapter
        }

        this.myBluetoothAdapter = bluetoothAdapter;
        this.bluetoothScanner = this.myBluetoothAdapter?.bluetoothLeScanner

        return this.myBluetoothAdapter?.isDisabled == false
    }

//    private fun scanLeDevice(enable: Boolean) {
//        if(this.bluetoothScanner == null){
//            // no scanner found
//            return
//        }
//        when (enable) {
//            true -> {
//                // Stops scanning after a pre-defined scan period.
//                handler.postDelayed({
//                    mScanning = false
//                    bluetoothScanner.stopScan(leSacnCallBack)
//                }, SCAN_PERIOD)
//                mScanning = true
//                bluetoothScanner.startScan(leScanCallback)
//            }
//            else -> {
//                mScanning = false
//                bluetoothScanner.stopScan(leScanCallback)
//            }
//        }
//    }
}
package vsec.com.slockandroid.Controllers.Callback

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult

object BluetoothScannCallback: ScanCallback() {
    private var scannedBleDevices: ArrayList<BluetoothDevice> = ArrayList()

    override fun onBatchScanResults(results: MutableList<ScanResult>?) {
        super.onBatchScanResults(results)
        this.scannedBleDevices = ArrayList()

        var iterator = results?.iterator()
        while (iterator!!.hasNext()){
            this.scannedBleDevices.add(iterator.next().device)
        }
        this.scannedBleDevices.distinctBy { it.address }
    }
}
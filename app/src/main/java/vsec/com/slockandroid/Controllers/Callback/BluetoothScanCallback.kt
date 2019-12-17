package vsec.com.slockandroid.Controllers.Callback

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import vsec.com.slockandroid.Controllers.BluetoothController


object BluetoothScanCallback: ScanCallback() {
    var scannedBleDevices: ArrayList<BluetoothDevice> = ArrayList()

    override fun onBatchScanResults(results: MutableList<ScanResult>?) {
        super.onBatchScanResults(results)
        this.scannedBleDevices = ArrayList()

        var iterator = results?.iterator()
        while (iterator!!.hasNext()){
            this.scannedBleDevices.add(iterator.next().device)
        }
        this.scannedBleDevices.distinctBy { it.address }

    }

    override fun onScanResult(callbackType: Int, result: ScanResult) {
        this.scannedBleDevices.add(result.device)
        this.scannedBleDevices = this.scannedBleDevices.distinctBy { it.address } as ArrayList<BluetoothDevice>
        //Log.e("",this.scannedBleDevices[0].name)
        //var b = this?.scannedBleDevices.find { it?.name.contains("SLOCK") }
        //if(b != null){
        //    Log.e("b", b?.name)
        //}
        //b?.connectGatt(BluetoothController.context,false,BluetoothGattConnectCallback)
    }

    //fun connectGatt(name: String){
    //    Log.e("test", "t: " + this.scannedBleDevices.find { it.name == name }?.name)
    //    this.scannedBleDevices.find { it.name == name }?.connectGatt(BluetoothController.context, false, BluetoothGattConnectCallback)
    //}
    fun clearScannedDevices(){
        this.scannedBleDevices.clear()
    }
}
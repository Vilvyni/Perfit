package com.epfl.esl.watch

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.epfl.esl.watch.databinding.ActivityMainBinding
import com.google.android.gms.wearable.*
import com.google.android.gms.wearable.R
import java.util.*
import kotlin.concurrent.timerTask


class MainActivity : Activity(), DataClient.OnDataChangedListener, SensorEventListener,
    MessageClient.OnMessageReceivedListener {

    private lateinit var binding: ActivityMainBinding

    var heartRate:Int = 40
    //private var timer = Timer()
    var cleanessOfOutfit:Int = 101

    var time:Int = 0

    lateinit var mainHandler: Handler   //sort of timer for the cleaness
    private val updateTextTask = object : Runnable {
        override fun run() {
            updateCleaness()
            mainHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // binding.textHRValue.setText(heartRate)

        binding.textHRValue.text = heartRate.toString()
        binding.textCleaness.text = time.toString()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                "android" + ""
                        + ".permission.BODY_SENSORS"
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissions(arrayOf("android.permission.BODY_SENSORS"), 0)
        }

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)?.also { heartRate ->
            sensorManager.registerListener(this, heartRate, SensorManager.SENSOR_DELAY_UI)
        }

        mainHandler = Handler(Looper.getMainLooper())

        mainHandler.removeCallbacks(updateTextTask)
    }


    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents
            .filter {it.dataItem.uri.path == "/userInfo" }
            .forEach { event ->
                val receivedCleanessTop: String = DataMapItem.fromDataItem(event.dataItem).dataMap.getString("username")

                //binding.myText.setText(receivedUsername)
            }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        Toast.makeText(this, "receive", Toast.LENGTH_SHORT).show()

        if(messageEvent.path == "/command") {
            val receivedCommand: String = String(messageEvent.data)
            if (receivedCommand == "Start") {
                mainHandler.post(updateTextTask)
            } else if (receivedCommand == "Stop") {
                mainHandler.removeCallbacks(updateTextTask)
            }
        }
        if(messageEvent.path == "/cleaness"){
            val receivedCleaness: String = String(messageEvent.data)
            Toast.makeText(this, receivedCleaness, Toast.LENGTH_SHORT).show()

            cleanessOfOutfit = receivedCleaness.toInt()
        }
    }

    override fun onResume() {
        super.onResume()
        Wearable.getDataClient(this).addListener(this)
        Wearable.getMessageClient(this).addListener(this)
        mainHandler.post(updateTextTask)
    }

    override fun onPause() {
        super.onPause()
        Wearable.getDataClient(this).removeListener(this)
        Wearable.getMessageClient(this).removeListener(this)
        mainHandler.removeCallbacks(updateTextTask)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        val heartRateReceived = event!!.values[0].toInt()
        binding.textHRValue.text = heartRateReceived.toString()
        heartRate = heartRateReceived
    }

    private fun sendDataToMobile(heartRate: Int) {
        val dataClient: DataClient = Wearable.getDataClient(this)
        val putDataReq: PutDataRequest = PutDataMapRequest.create("/heart_rate").run {
            dataMap.putInt("HEART_RATE", heartRate)
            asPutDataRequest()
        }
        dataClient.putDataItem(putDataReq)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun updateCleaness(){
        //time = time + 1
        cleanessOfOutfit = cleanessOfOutfit - heartRate/20

        if (cleanessOfOutfit < 0) cleanessOfOutfit = 0

        binding.textCleaness.text = cleanessOfOutfit.toString()

        sendDataToMobile(heartRate)
    }

}
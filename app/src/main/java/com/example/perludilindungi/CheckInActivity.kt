package com.example.perludilindungi

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.perludilindungi.databinding.ActivityCheckinBinding

class CheckInActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityCheckinBinding
    private lateinit var sensorManager: SensorManager
    private var ambientTemp: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val backBtn = binding.backBtnCheckIn

        /*
        * For going back to previous activity
        * */
        backBtn.setOnClickListener {
            finish()
        }

        setUpSensor()
    }

    /*
    * Initialize sensor manager
    * */
    private fun setUpSensor(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        ambientTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        // Check if ambient temperature sensor exist
        if (ambientTemp !== null) {
            sensorManager.registerListener(this, ambientTemp, SensorManager.SENSOR_DELAY_FASTEST)
        } else {
            binding.textTemp.text = "No ambient sensor detected"
        }
    }
    /*
    * Event listener on sensor change
    * */
    override fun onSensorChanged(p0: SensorEvent?) {
        val curTemp = p0?.values?.get(0)
        if (curTemp !== null) {
            binding.textTemp.text = "${curTemp.toString()} \u2103"
        }
    }
    /*
    * Event listener on accuracy change
    * */
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    /*
    * Event listener on activity resume
    * */
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, ambientTemp, SensorManager.SENSOR_DELAY_FASTEST)
    }

    /*
    * Event listener on activity start
    * */
    override fun onStart() {
        super.onStart()
        sensorManager.registerListener(this, ambientTemp, SensorManager.SENSOR_DELAY_FASTEST)
    }

    /*
    * Event listener on activity pause
    * */
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    /*
    * Event listener on activity stop
    * */
    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }
}
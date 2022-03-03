package com.example.perludilindungi.services

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.TextView

open class TemperatureService(context: Context, textView: TextView): SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var ambientTemp: Sensor? = null

    protected var context = context
    private var textView = textView

    fun setUpSensor() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        ambientTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        // Check if ambient temperature sensor exist
        if (ambientTemp !== null) {
            sensorManager.registerListener(this, ambientTemp, SensorManager.SENSOR_DELAY_FASTEST)
        } else {
            textView.text = "\u2103"
        }
    }

    fun unregister() {
        sensorManager.unregisterListener(this)
    }

    fun register() {
        sensorManager.registerListener(this, ambientTemp, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val curTemp = p0?.values?.get(0)
        if (curTemp !== null) {
            val temperature = "$curTemp \u2103"
            textView.text = temperature
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}
package com.example.perludilindungi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.perludilindungi.databinding.ActivityCheckinBinding
import com.example.perludilindungi.model.CheckIn
import com.example.perludilindungi.network.RetrofitService
import com.example.perludilindungi.repository.Repository
import com.example.perludilindungi.services.TemperatureService
import com.google.android.gms.location.*
import com.google.zxing.BarcodeFormat

private const val PERMISSION_REQUEST_CODE = 101

class CheckInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckinBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var temperatureService: TemperatureService
    private lateinit var viewModel:MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private var qrCode = ""
    private var latitude: Double? = null
    private var longitude: Double? = null
    private lateinit var checkIn: CheckIn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backBtn = binding.backBtnCheckIn
        backBtn.setOnClickListener {
            finish()
        }

        supportActionBar?.hide()
        // Set temperature
        temperatureService = TemperatureService(this, binding.textTemp)
        temperatureService.setUpSensor()

        // Set permission for scanner
        setUpPermission()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        codeScanner()

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(retrofitService))
        )[MainViewModel::class.java]

        viewModel.checkInResult.observe(this) { response ->
            try{
                if (response.data?.userStatus == "red"
                    || response.data?.userStatus == "red"){
                    binding.scanResult.text = response.data.reason
                } else{
                    // do nothing
                }



            } catch (e: Exception) {
                Log.e("Retro", e.localizedMessage)
                binding.scanResult.text = "Invalid QR Code"
            }
        }

        viewModel.failMsg.observe(this) {

        }
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, binding.scannerView)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = listOf(BarcodeFormat.QR_CODE)

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread{
                    if (it.text != ""){
                        qrCode = it.text
                        fetchLocation()
                    }
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread{
                    Log.e("CheckIn", "Camera error")
                }
            }
        }

        binding.scannerView.setOnClickListener{
            codeScanner.startPreview()
        }
    }

    /*
    * Event listener on activity resume
    * */
    override fun onResume() {
        super.onResume()
        temperatureService.register()
        codeScanner.startPreview()
    }

    /*
    * Event listener on activity start
    * */
    override fun onStart() {
        super.onStart()
        temperatureService.register()
        codeScanner.startPreview()
    }


    /*
    * Event listener on activity pause
    * */
    override fun onPause() {
        super.onPause()
        temperatureService.unregister()
        codeScanner.releaseResources()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    /*
    * Event listener on activity stop
    * */
    override fun onStop() {
        super.onStop()
        codeScanner.releaseResources()
        temperatureService.unregister()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun setUpPermission() {
        var listPermission = listOf(Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        var arrayRequest: MutableList<String> = arrayListOf()
        for (permission in listPermission) {
            var res = ContextCompat.checkSelfPermission(this, permission)

            if (res != PackageManager.PERMISSION_GRANTED) {
                arrayRequest.add(permission)
            }
        }

        if (arrayRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(this, arrayRequest.toTypedArray(), PERMISSION_REQUEST_CODE)
        }

    }

    private fun fetchLocation() {
        if (checkAllPermission()){
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful && task.result != null) {
                            val location = task.result
                            try{
//                              startLocationUpdates()
                                viewModel.checkIn(qrCode, location.latitude, location.longitude)
                            } catch (e: Exception) {
                                e.message?.let { Log.e("Error", it) }
                            }
                        } else {
                            try{
                                startLocationUpdates()
                            } catch (e: Exception) {
                                e.message?.let { Log.e("Error", it) }
                            }
                        }
                    }
            } else {
                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
            }
        } else {
            setUpPermission()
        }
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 0
            fastestInterval = 0
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    var result = 0
                    for (item in grantResults) {
                        result += item
                    }
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(
                            this,
                            "You need to enable all permission to use this feature",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // good
                    }
                }
            }
        }
    }

    private fun checkAllPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }

        return false
    }

    private fun isLocationEnabled(): Boolean{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            if (locationResult.locations.isNotEmpty()){
                binding.userPosition.text = "${locationResult.locations[0].latitude} ${locationResult.locations[0].longitude}"
                with(viewModel) {
                    checkIn(qrCode, locationResult.locations.last().latitude, locationResult.locations.last().longitude)
                }

            }

        }
    }
}
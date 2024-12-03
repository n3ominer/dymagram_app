package com.example.dymagram.pages

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.dymagram.R
import com.example.dymagram.app_services.AirplaneBroadcastReceiver
import com.example.dymagram.app_services.DymaSyncService
import com.example.dymagram.di.injectAppConfiguration
import com.example.dymagram.di.injectModuleDependencies
import com.example.dymagram.isPermissionNotGranted
import com.example.dymagram.pages.interfaces.PagerHandler
import com.example.dymagram.views.ViewPagerAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class HomeActivity : AppCompatActivity(), PagerHandler {
    private lateinit var dymagramPager: ViewPager2
    private val airplaneBroadcastReceiver = AirplaneBroadcastReceiver()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationRequest = LocationRequest.create().apply {
        interval = 10000 // 10 sec
        fastestInterval = 5000 // 5 sec
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private var locationIsObserved: Boolean = false

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locatoinResult: LocationResult) {
            super.onLocationResult(locatoinResult)
                displayLocationToast(locatoinResult.lastLocation)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(
            airplaneBroadcastReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )

        sendBroadcast(Intent("ACTION_TEST_INTENT"))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat
                .requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Injection des dépendances
        injectAppConfiguration()
        injectModuleDependencies(this@HomeActivity)

        setUpMainPager()

        Intent(this, DymaSyncService::class.java).also {
            it.action = DymaSyncService.Actions.START.toString()
            startService(it)
        }
    }

    private fun setUpMainPager() {
        this.dymagramPager = findViewById(R.id.main_app_pager)

        val mainPagerAdapter = ViewPagerAdapter(this, this)
        this.dymagramPager.adapter = mainPagerAdapter

        this.dymagramPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if(position != 0) {
                    stopLocationUpdates()
                } else {
                    getUserLocation()
                }
            }
        })
        displayHomePage()
    }

    override fun displayMediaPage() {
        this.dymagramPager.currentItem = 0
    }

    override fun displayHomePage() {
        this.dymagramPager.currentItem = 1
    }

    override fun displayDirectMessagesPage() {
        this.dymagramPager.currentItem = 2
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneBroadcastReceiver)
    }


    private fun stopLocationUpdates() {
        if(!locationIsObserved) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        if (this.isPermissionNotGranted()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            displayLocationToast(location)
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

    }

    private fun displayLocationToast(location: Location?) {
        location?.let {
            val long = it.longitude
            val lat = it.latitude

            Toast.makeText(this@HomeActivity, "Votre position est" +
                    "(Longitude: ${long.toString().takeLast(3)}, Latitude: ${lat.toString().takeLast(3)}",
                Toast.LENGTH_LONG)
                .show()
        }
    }
}


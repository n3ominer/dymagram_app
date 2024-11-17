package com.example.dymagram.pages

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
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
import com.example.dymagram.pages.interfaces.PagerHandler
import com.example.dymagram.views.ViewPagerAdapter

class HomeActivity : AppCompatActivity(), PagerHandler {
    private lateinit var dymagramPager: ViewPager2

    private val airplaneBroadcastReceiver = AirplaneBroadcastReceiver()

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

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
}


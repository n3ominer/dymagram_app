package com.example.dymagram.pages

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.dymagram.R
import com.example.dymagram.views.ViewPagerAdapter

class HomeActivity : AppCompatActivity() {
    private lateinit var dymagramPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpMainPager()
    }

    private fun setUpMainPager() {
        this.dymagramPager = findViewById(R.id.main_app_pager)

        val mainPagerAdapter = ViewPagerAdapter(this)
        this.dymagramPager.adapter = mainPagerAdapter
        this.dymagramPager.currentItem = 1
    }

}


package com.example.dymagram.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dymagram.HomeActivity
import com.example.dymagram.R

class DymaGramLoginActivity : AppCompatActivity() {

    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dyma_gram_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpUi()
    }

    private fun setUpUi() {
        this.loginButton = findViewById(R.id.login_button)
        this.loginButton.setOnClickListener {
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}
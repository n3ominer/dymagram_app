package com.example.dymagram.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dymagram.pages.HomeActivity
import com.example.dymagram.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class DymaGramLoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button

    private lateinit var userLoginTextview: TextInputEditText
    private lateinit var userPasswordTextView: TextInputEditText

    // Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dyma_gram_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupFirebase()
        setUpUi()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun setUpUi() {
        this.loginButton = findViewById(R.id.login_button)
        this.loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(
                this.userLoginTextview.text.toString(),
                this.userPasswordTextView.text.toString()
            ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d("Firebase Auth", "signInWithEmail:success")
                        val user = auth.currentUser
                        Intent(this, HomeActivity::class.java).also {
                            startActivity(it)
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w("Firebase Auth", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }

        this.userLoginTextview = findViewById(R.id.login_text_input_edit_text)
        this.userPasswordTextView = findViewById(R.id.password_text_input_edit_text)
    }

    private fun setupFirebase() {
        auth = Firebase.auth

    }
}
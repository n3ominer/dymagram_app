package com.example.dymagram.pages

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dymagram.R

class UserStoryDetailActivity : AppCompatActivity() {

    companion object UserStoryIntentData {
        const val STORIES_CONTENT_URL = "STORIES_CONTENT_URL"
        const val USER_NAME = "USER_NAME"
        const val USER_PSEUDO = "USER_PSEUDO"
        const val USER_PROFILE_PIC_URL = "USER_PROFILE_PIC_URL"
    }

    private lateinit var storiesContentUrl: List<String>
    private lateinit var userName: String
    private lateinit var userPseudo: String
    private lateinit var userProfilePictureUrl: String

    private lateinit var storyContentImageView: ImageView
    private lateinit var storyUserProfileImageView: ImageView
    private lateinit var storyUserNameTextView: TextView
    private lateinit var storyUserPseudoTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_story_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getIntentData()
        setUpViews()
        fillViews()
    }

    private fun getIntentData() {
        this.storiesContentUrl = intent.getStringArrayListExtra("STORIES_CONTENT_URL") ?: listOf()
        this.userName = intent.getStringExtra("USER_NAME") ?: ""
        this.userPseudo = intent.getStringExtra("USER_PSEUDO") ?: ""
        this.userProfilePictureUrl = intent.getStringExtra("USER_PROFILE_PIC_URL") ?: ""
    }

    private fun setUpViews() {
        this.storyContentImageView = findViewById(R.id.story_content_image_view)
        this.storyUserProfileImageView = findViewById(R.id.story_user_profile_ic_image_view)
        this.storyUserNameTextView = findViewById(R.id.story_user_name_text_view)
        this.storyUserPseudoTextView = findViewById(R.id.story_user_pseudo_text_view)
    }

    private fun fillViews() {
        Glide.with(this)
            .load("https://robohash.org/${this.userName}")
            .into(this.storyUserProfileImageView)
        Glide.with(this)
            .load(this.storiesContentUrl.first())
            .into(this.storyContentImageView)
        this.storyUserNameTextView.text = this.userName
        this.storyUserPseudoTextView.text = this.userPseudo
    }

}
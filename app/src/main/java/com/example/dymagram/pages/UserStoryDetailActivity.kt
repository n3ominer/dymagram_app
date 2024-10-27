package com.example.dymagram.pages

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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

    private lateinit var previousStoryClickableView: View
    private lateinit var nextStoryClickableView: View

    private var storyPosition = 0

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
        setUpStoryNavigationViews()
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
        displaySotryContent()
        this.storyUserNameTextView.text = this.userName
        this.storyUserPseudoTextView.text = this.userPseudo
    }

    private fun setUpStoryNavigationViews() {
        this.previousStoryClickableView = findViewById(R.id.previous_story_view)
        this.previousStoryClickableView.setOnClickListener {
            if (this.storyPosition - 1 >= 0) {
                this.storyPosition -= 1
                this.displaySotryContent()
            } else {
                finish()
            }
        }
        this.previousStoryClickableView.setOnLongClickListener {
            Toast.makeText(this, "Écoulement du temps de la story mis en pause", Toast.LENGTH_LONG).show()
            true
        }

        this.nextStoryClickableView = findViewById(R.id.next_story_view)
        this.nextStoryClickableView.setOnClickListener {
            if (this.storyPosition + 1 < this.storiesContentUrl.size) {
                this.storyPosition += 1
                this.displaySotryContent()
            } else {
                finish()
            }
        }
        this.nextStoryClickableView.setOnLongClickListener {
            Toast.makeText(this, "Écoulement du temps de la story mis en pause", Toast.LENGTH_LONG).show()
            true
        }
    }


    private fun displaySotryContent() {
        Glide.with(this)
            .load(this.storiesContentUrl[this.storyPosition])
            .into(this.storyContentImageView)
    }
}
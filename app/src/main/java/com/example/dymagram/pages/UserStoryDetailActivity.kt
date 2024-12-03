package com.example.dymagram.pages

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dymagram.R
import com.example.dymagram.app_services.DymaSyncService

class UserStoryDetailActivity : AppCompatActivity() {

    companion object UserStoryIntentData {
        const val STORIES_CONTENT_URL = "STORIES_CONTENT_URL"
        const val STORIES_DURATION = "STORIES_DURATION"
        const val USER_NAME = "USER_NAME"
        const val USER_PSEUDO = "USER_PSEUDO"
        const val USER_PROFILE_PIC_URL = "USER_PROFILE_PIC_URL"
        const val NOTIFICATION_CHANNEL_ID = "dymagram_notification_channel"
    }

    private lateinit var storiesContentUrl: List<String>
    private lateinit var storiesDurations: List<Int>
    private lateinit var userName: String
    private lateinit var userPseudo: String
    private lateinit var userProfilePictureUrl: String

    private lateinit var storyContentImageView: ImageView
    private lateinit var storyUserProfileImageView: ImageView
    private lateinit var storyUserNameTextView: TextView
    private lateinit var storyUserPseudoTextView: TextView

    private lateinit var previousStoryClickableView: View
    private lateinit var nextStoryClickableView: View

    private lateinit var progressContainer: LinearLayout
    private val progressBars = mutableListOf<ProgressBar>()

    private var storyPosition = 0

    val handler = Handler(Looper.getMainLooper())

    private lateinit var notificationChannel: NotificationChannel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_story_detail)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Lecture de stories complété",
                NotificationManager.IMPORTANCE_HIGH
            )
        }

        getIntentData()

        setUpViews()
        fillViews()
        setUpStoryNavigationViews()

        Intent(this, DymaSyncService::class.java).also {
            it.action = DymaSyncService.Actions.STOP.toString()
            startService(it)
        }
    }

    private fun getIntentData() {
        this.storiesContentUrl = intent.getStringArrayListExtra("STORIES_CONTENT_URL") ?: listOf()
        this.storiesDurations = intent.getIntegerArrayListExtra("STORIES_DURATION") ?: listOf()
        this.userName = intent.getStringExtra("USER_NAME") ?: ""
        this.userPseudo = intent.getStringExtra("USER_PSEUDO") ?: ""
        this.userProfilePictureUrl = intent.getStringExtra("USER_PROFILE_PIC_URL") ?: ""
    }

    private fun setUpViews() {
        progressContainer = findViewById(R.id.progressContainer)
        this.storyContentImageView = findViewById(R.id.story_content_image_view)
        this.storyUserProfileImageView = findViewById(R.id.story_user_profile_ic_image_view)
        this.storyUserNameTextView = findViewById(R.id.story_user_name_text_view)
        this.storyUserPseudoTextView = findViewById(R.id.story_user_pseudo_text_view)
    }

    private fun fillViews() {
        addProgressSegments(storiesDurations.size)
        startProgress()
        Glide.with(this)
            .load("https://robohash.org/${this.userName}")
            .into(this.storyUserProfileImageView)
        displaySotryContent()
        this.storyUserNameTextView.text = this.userName
        this.storyUserPseudoTextView.text = this.userPseudo
    }

    private fun setUpStoryNavigationViews() {
        this.previousStoryClickableView = findViewById(R.id.previous_story_view)
        this.previousStoryClickableView.apply {
            setOnClickListener {
                previousProgressSegment()
            }
        }

        this.nextStoryClickableView = findViewById(R.id.next_story_view)
        this.nextStoryClickableView.apply {
            setOnClickListener {
                nextProgressSegment()
            }
        }
    }


    private fun displaySotryContent() {
        Glide.with(this)
            .load(this.storiesContentUrl[this.storyPosition])
            .into(this.storyContentImageView)
    }

    private fun addProgressSegments(numberOfSegments: Int) {
        // Définir le weightSum du LinearLayout parent
        progressContainer.weightSum = numberOfSegments.toFloat()

        for (i in 0 until numberOfSegments) {
            val progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
                layoutParams = LinearLayout.LayoutParams(0, 8.dpToPx(), 1f).apply {
                    setMargins(4, 0, 4, 0) // Margins entre les segments
                }
                max = storiesDurations[i] * 10
                progress = 0
            }
            progressBars.add(progressBar)
            progressContainer.addView(progressBar)
        }
    }

    private fun startProgress() {
        handler.removeCallbacksAndMessages(null)
        handler.post(object : Runnable {
            override fun run() {
                if (storyPosition < progressBars.size) {
                    val progressBar = progressBars[storyPosition]
                    progressBar.progress += 1 // Incrémente progressivement

                    if (progressBar.progress >= storiesDurations[storyPosition] * 10) {
                        storyPosition++ // Passe au segment suivant
                        displayNextStory()
                    }
                    handler.postDelayed(this, 100)
                }
            }
        })
    }


    private fun nextProgressSegment() {
        storyPosition += 1
        if (storyPosition < progressBars.size && storyPosition >= 0){
            progressBars[storyPosition - 1].progress = storiesDurations[storyPosition - 1] * 10
            progressBars[storyPosition].progress = 0
            startProgress()
        }
        displayNextStory()

    }

    private fun previousProgressSegment() {
        progressBars[storyPosition].progress = 0
        storyPosition -= 1
        if (storyPosition >= 0){
            progressBars[storyPosition].progress = 0
            startProgress()
        }
        displayPreviousStory()
    }


    private fun displayNextStory() {
        if (this.storyPosition  < this.storiesContentUrl.size) {
            this.displaySotryContent()
        } else {
            val actionIntent = Intent(this, HomeActivity::class.java).apply {

            }

            val actionPendingIntent = PendingIntent.getActivity(
                this,
                0,
                actionIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            // Créer la notification
            val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.dymagram_logo)
                .setContentTitle("Vous avez fini la story de ${this.userName}")
                .setContentText("Vous avez vu toutes les stories du jour de ${this.userName}")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.baseline_like_24, "J'aime", actionPendingIntent)
                .build()

            // Publier

            val notificationManager = NotificationManagerCompat.from(this)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
             return
            }
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(1, notification)

            finish()
        }
    }

    private fun displayPreviousStory() {
        if (this.storyPosition >= 0) {
            this.displaySotryContent()
        } else {
            finish()
        }
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private fun sendFinishedWatchingUserStory() {
        //this.userName


    }
}
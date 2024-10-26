package com.example.dymagram.views.viewholders.home_vh

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dymagram.R

class StoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val userprofilePicImageView: ImageView = itemView.findViewById(R.id.user_profile_pic_image_view)
    val usernameTextView: TextView = itemView.findViewById(R.id.story_username_tv)

}
package com.example.dymagram.views.recycler_view_adapters.home_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dymagram.R
import com.example.dymagram.data.model.story.Story
import com.example.dymagram.views.viewholders.home_vh.StoryViewHolder

class StoryRvAdapter(val stories: List<Story>): RecyclerView.Adapter<StoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_story_view_holder, parent, false)

        return StoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stories.size + 1
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        if(position > 0) {
            val storyData = this.stories[position - 1]
            // Binding Viewholder avec le model

            holder.usernameTextView.text = storyData.username
            Glide
                .with(holder.itemView)
                .load("https://robohash.org/${storyData.username}")
                .into(holder.userprofilePicImageView);

        } else {
            holder.usernameTextView.text = "DymaUser"
        }

    }
}
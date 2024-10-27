package com.example.dymagram.views.recycler_view_adapters.home_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dymagram.R
import com.example.dymagram.data.model.story.Story
import com.example.dymagram.pages.interfaces.StoryClickHandler
import com.example.dymagram.views.viewholders.home_vh.StoryViewHolder

class StoryRvAdapter(private val stories: List<Story>,
                     private val storyClickHandler: StoryClickHandler): RecyclerView.Adapter<StoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_story_view_holder, parent, false)

        return StoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stories.size + 10
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        if (position == 0) {
            holder.userAddStoryImageView.visibility = View.VISIBLE
            holder.userAddStoryImageView.setOnClickListener {
                this.storyClickHandler.addStory()
            }

        } else {
            holder.userAddStoryImageView.visibility = View.GONE
        }
        if(position in 1..3) {
            val storyData = this.stories[position - 1]
            // Binding Viewholder avec le model

            holder.usernameTextView.text = storyData.username
            Glide
                .with(holder.itemView)
                .load("https://robohash.org/${storyData.username}")
                .into(holder.userprofilePicImageView)
            holder.storyCellLayout.setOnClickListener {
                this.storyClickHandler.displayStoryContent(
                    storyData.story_content.map { it.url },
                    storyData.username,
                    storyData.user_id,
                    storyData.profile_picture
                )
            }

        } else {
            Glide
                .with(holder.itemView)
                .load("https://robohash.org/${position}")
                .into(holder.userprofilePicImageView)
            holder.usernameTextView.text = "DymaUser_$position"
        }

    }
}
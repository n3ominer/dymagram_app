package com.example.dymagram.data.model

import com.example.dymagram.data.model.following.Following
import com.example.dymagram.data.model.messages.Message
import com.example.dymagram.data.model.posts.Post
import com.example.dymagram.data.model.story.Story

data class GlobalDataModel(
    val followings: List<Following>,
    val messages: List<Message>,
    val posts: List<Post>,
    val stories: List<Story>
)
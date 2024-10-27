package com.example.dymagram.pages.interfaces

interface StoryClickHandler {

    fun displayStoryContent(storiesUrls: List<String>, username: String, pseudo: String, userProfilePicUrl: String)
    fun addStory()
}
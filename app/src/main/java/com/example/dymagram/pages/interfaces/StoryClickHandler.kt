package com.example.dymagram.pages.interfaces

interface StoryClickHandler {

    fun displayStoryContent(storiesUrls: List<String>, storiesDuration: List<Int>, username: String, pseudo: String, userProfilePicUrl: String)
    fun addStory()
}
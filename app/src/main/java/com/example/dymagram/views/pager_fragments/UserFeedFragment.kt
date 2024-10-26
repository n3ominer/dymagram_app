package com.example.dymagram.views.pager_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dymagram.R
import com.example.dymagram.data.model.GlobalDataModel
import com.example.dymagram.data.model.posts.Post
import com.example.dymagram.data.model.story.Story
import com.example.dymagram.repositories.GlobalDataRepository
import com.example.dymagram.viewmodel.HomeFeedViewModel
import com.example.dymagram.viewmodel.factories.HomeFeedViewModelFactory
import com.example.dymagram.views.recycler_view_adapters.home_adapters.PostsRvAdapter
import com.example.dymagram.views.recycler_view_adapters.home_adapters.StoryRvAdapter

class UserFeedFragment : Fragment() {

    private lateinit var storiesRv: RecyclerView
    private lateinit var postsRv: RecyclerView

    private val homeFeedViewModel: HomeFeedViewModel by viewModels {
        HomeFeedViewModelFactory(GlobalDataRepository(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_feed, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData(view)
    }

    private fun setUpStoriesRv(stories: List<Story>, fragmentView: View) {
        this.storiesRv = fragmentView.findViewById(R.id.user_feed_stories_recycler_view)
        this.storiesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        this.storiesRv.adapter = StoryRvAdapter(stories)

    }

    private fun setUpPostsRv(posts: List<Post>, fragmentView: View) {
        this.postsRv = fragmentView.findViewById(R.id.user_feed_posts_recycler_view)
        this.postsRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        this.postsRv.adapter = PostsRvAdapter(posts)
    }

    private fun fetchData(fragmentView: View) {
        this.homeFeedViewModel.globalData.observe(viewLifecycleOwner) { data ->
            Toast.makeText(context, "On a reçu de la donnée", Toast.LENGTH_LONG).show()

            // Enlever le loader un loader
            this.setUpStoriesRv(getUserFeedStories(data), fragmentView)
            this.setUpPostsRv(getUserFeedPosts(data), fragmentView)
        }

        this.homeFeedViewModel.fetchDataFromFakeServer()
    }

    private fun getUserFeedStories(data: GlobalDataModel): List<Story> {
        return data.stories
    }

    private fun getUserFeedPosts(data: GlobalDataModel): List<Post> {
        return data.posts
    }
}
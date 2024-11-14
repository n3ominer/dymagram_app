package com.example.dymagram.views.pager_fragments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dymagram.R
import com.example.dymagram.data.model.GlobalDataModel
import com.example.dymagram.data.model.posts.Post
import com.example.dymagram.data.model.story.Story
import com.example.dymagram.pages.UserStoryDetailActivity
import com.example.dymagram.pages.interfaces.PagerHandler
import com.example.dymagram.pages.interfaces.StoryClickHandler
import com.example.dymagram.viewmodel.HomeFeedViewModel
import com.example.dymagram.views.recycler_view_adapters.home_adapters.PostsRvAdapter
import com.example.dymagram.views.recycler_view_adapters.home_adapters.StoryRvAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class UserFeedFragment : Fragment(), StoryClickHandler {

    private lateinit var storiesRv: RecyclerView
    private lateinit var postsRv: RecyclerView

    private lateinit var retryLoadingLinearLayout: LinearLayout
    private lateinit var mainFragmentLayout: RelativeLayout

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val homeFeedViewModel: HomeFeedViewModel by viewModel()

    private lateinit var _pagerHandler: PagerHandler

    companion object {
        fun newInstance(pageHandler: PagerHandler): UserFeedFragment {
            return UserFeedFragment().also {
                it._pagerHandler = pageHandler
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_feed, container, false)
        this.swipeRefreshLayout = view.findViewById(R.id.home_fragment_swipe_refresh_layout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mainFragmentLayout = view.findViewById(R.id.main_fragment_layout)

        fetchData(view)
        setUpSwipeToRefreshListeners()
        setUpReloadDataViews(view)
    }

    private fun setUpStoriesRv(stories: List<Story>, fragmentView: View) {
        this.storiesRv = fragmentView.findViewById(R.id.user_feed_stories_recycler_view)
        this.storiesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        this.storiesRv.adapter = StoryRvAdapter(stories, this)
        this.storiesRv.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    val canScrollHorizontally = rv.canScrollHorizontally(1) || rv.canScrollHorizontally(-1)

                    rv.parent.requestDisallowInterceptTouchEvent(canScrollHorizontally)

                    return false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                    // Pas de traitement nécessaire ici
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                    // Pas de traitement nécessaire ici
                }
            })

    }

    private fun setUpPostsRv(posts: List<Post>, fragmentView: View) {
        this.postsRv = fragmentView.findViewById(R.id.user_feed_posts_recycler_view)
        this.postsRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        this.postsRv.adapter = PostsRvAdapter(posts)
    }

    private fun fetchData(fragmentView: View) {
        this.homeFeedViewModel.globalData.observe(viewLifecycleOwner) { data ->

            this.mainFragmentLayout.visibility = View.VISIBLE
            this.retryLoadingLinearLayout.visibility = View.GONE
            this.setUpStoriesRv(getUserFeedStories(data), fragmentView)
            this.setUpPostsRv(getUserFeedPosts(data), fragmentView)
            this.swipeRefreshLayout.isRefreshing = false
        }

        this.homeFeedViewModel.error.observe(viewLifecycleOwner) { error ->
            this.mainFragmentLayout.visibility = View.GONE
            this.retryLoadingLinearLayout.visibility = View.VISIBLE
            Log.d("Error Dymagram", error)
            Toast.makeText(context, "Error while trying de fetch data, try again", Toast.LENGTH_LONG).show()
        }

        this.homeFeedViewModel.fetchDataFromFakeServer()
    }

    private fun getUserFeedStories(data: GlobalDataModel): List<Story> {
        return data.stories
    }

    private fun getUserFeedPosts(data: GlobalDataModel): List<Post> {
        return data.posts
    }

    override fun displayStoryContent(storiesUrls: List<String>, storiesDuration: List<Int>, username: String, pseudo: String, userProfilePicUrl: String) {
        Intent(this.context, UserStoryDetailActivity::class.java).also {
            val options = ActivityOptions.makeCustomAnimation(requireContext(), R.anim.slide_up_animation, R.anim.slide_down_animation)
            it.putStringArrayListExtra(UserStoryDetailActivity.STORIES_CONTENT_URL, ArrayList(storiesUrls))
            it.putIntegerArrayListExtra(UserStoryDetailActivity.STORIES_DURATION, ArrayList(storiesDuration))
            it.putExtra(UserStoryDetailActivity.USER_NAME, username)
            it.putExtra(UserStoryDetailActivity.USER_PSEUDO, pseudo)
            it.putExtra(UserStoryDetailActivity.USER_PROFILE_PIC_URL, userProfilePicUrl)
            startActivity(it, options.toBundle())
        }
    }

    override fun addStory() {
        this._pagerHandler.displayMediaPage()
    }

    private fun setUpSwipeToRefreshListeners() {
        this.swipeRefreshLayout.setOnRefreshListener {
            this.homeFeedViewModel.fetchDataFromFakeServer()
        }
    }

    private fun setUpReloadDataViews(fragmentView: View) {
        this.retryLoadingLinearLayout = fragmentView.findViewById(R.id.retry_loading_linear_layout)
        this.retryLoadingLinearLayout.setOnClickListener {
            this.homeFeedViewModel.fetchDataFromFakeServer()
        }
    }
}
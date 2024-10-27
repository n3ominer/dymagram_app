package com.example.dymagram.views

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dymagram.pages.HomeActivity
import com.example.dymagram.views.pager_fragments.DirectMessagesFragment
import com.example.dymagram.views.pager_fragments.MediaFragments
import com.example.dymagram.views.pager_fragments.UserFeedFragment

class ViewPagerAdapter(val activity: HomeActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MediaFragments()
            1 -> UserFeedFragment()
            2 -> DirectMessagesFragment()
            else -> UserFeedFragment()
        }
    }


}
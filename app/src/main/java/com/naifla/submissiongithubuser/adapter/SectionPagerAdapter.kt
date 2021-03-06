package com.naifla.submissiongithubuser.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.naifla.submissiongithubuser.R
import com.naifla.submissiongithubuser.fragment.FollowerFragment

import com.naifla.submissiongithubuser.fragment.FollowingFragment

class SectionPagerAdapter (private val mContext : Context,fm : FragmentManager)
    : FragmentPagerAdapter (fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val TAB_TITLES = intArrayOf(R.string.follower,R.string.following)
    var username : String? = null

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FollowerFragment.newInstance(username)
            1 -> fragment= FollowingFragment.newInstance(username)
        }

        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }


}
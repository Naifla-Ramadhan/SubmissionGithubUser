package com.naifla.submissiongithubuser.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.naifla.submissiongithubuser.R
import com.naifla.submissiongithubuser.adapter.MainAdapter
import com.naifla.submissiongithubuser.model.UserModel
import com.naifla.submissiongithubuser.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_following.*

/**
 * A simple [Fragment] subclass.
 */
class FollowingFragment : Fragment() {

    private lateinit var detailViewModel : DetailViewModel
    private lateinit var mainAdapter: MainAdapter

    companion object{
        val USERNAME_KEY = "username"

        fun newInstance (username : String?) : FollowingFragment{

            val bundle = Bundle()
            bundle.putString(USERNAME_KEY,username)
            val following = FollowingFragment()
            following.arguments= bundle
            return following
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(USERNAME_KEY)
        mainAdapter = MainAdapter()
        setupRecyclerview()

        detailViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)
        setupFollowing(username)
    }

    private fun setupRecyclerview (){
        rv_following.setHasFixedSize(true)
        mainAdapter.notifyDataSetChanged()
        rv_following.layoutManager = LinearLayoutManager(context)
        rv_following.adapter = mainAdapter
    }

    private fun setupFollowing(username :String?){
        detailViewModel.setFollowing(username)

        detailViewModel.getListUser().observe(this, Observer { UserModel ->
            if (UserModel != null){
                mainAdapter.setData(UserModel)
            }
        })
    }


}

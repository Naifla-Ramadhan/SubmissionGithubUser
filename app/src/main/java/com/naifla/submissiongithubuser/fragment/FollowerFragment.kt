package com.naifla.submissiongithubuser.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.naifla.submissiongithubuser.R
import com.naifla.submissiongithubuser.adapter.MainAdapter
import com.naifla.submissiongithubuser.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_follower.*

/**
 * A simple [Fragment] subclass.
 */
class FollowerFragment : Fragment() {

    private lateinit var detailViewModel : DetailViewModel
    private lateinit var adapterMain : MainAdapter

    companion object{
        val USERNAME_KEY = "username"

        fun newInstance(username : String?) : FollowerFragment{
            val bundle = Bundle()
            bundle.putString(USERNAME_KEY,username)
            val followers = FollowerFragment()
            followers.arguments = bundle
            return followers
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterMain = MainAdapter()
        setupRecyclerView()
        detailViewModel = ViewModelProvider (this,ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)
        val username = arguments?.getString(USERNAME_KEY)
        setupFollower(username)

    }

    private fun setupRecyclerView(){
        rv_follower.setHasFixedSize(true)
        adapterMain.notifyDataSetChanged()
        rv_follower.layoutManager = LinearLayoutManager(context)
        rv_follower.adapter = adapterMain
    }

    private fun setupFollower(username : String?){
        detailViewModel.setFollower(username)

        detailViewModel.getListUser().observe(this, Observer { UserModel ->
            if (UserModel != null){
                adapterMain.setData(UserModel)
            }
        })
    }

}

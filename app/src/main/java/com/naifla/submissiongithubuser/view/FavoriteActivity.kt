package com.naifla.submissiongithubuser.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naifla.submissiongithubuser.viewmodel.FavoriteViewModel
import com.naifla.submissiongithubuser.R
import com.naifla.submissiongithubuser.adapter.FavoriteAdapter
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_favorite.progress_bar

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        favoriteViewModel = ViewModelProvider(this)
            .get(FavoriteViewModel::class.java)

        showLoading(true)
        getFavorite()
        setupRecyclerView()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu_setting,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.setting){
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView(){
        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()
        rv_favorite.adapter = adapter
        rv_favorite.layoutManager = LinearLayoutManager(this)
    }

    private fun getFavorite(){
        favoriteViewModel.getFavorite()?.observe(this, Observer { Favorite ->
            if (Favorite != null){
                adapter.setData(Favorite)
                showLoading(false)
            }
        })
    }
    private fun showLoading(state :Boolean){
        if (state){
            progress_bar.visibility = View.VISIBLE
        }else{
            progress_bar.visibility = View.GONE
        }
    }
}

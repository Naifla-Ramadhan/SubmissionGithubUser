package com.naifla.submissiongithubuser.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naifla.submissiongithubuser.R
import com.naifla.submissiongithubuser.adapter.MainAdapter
import com.naifla.submissiongithubuser.model.UserModel
import com.naifla.submissiongithubuser.utility.Componen
import com.naifla.submissiongithubuser.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var  mainViewModel : MainViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainAdapter = MainAdapter()
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        searchUsers()
        setupRecyclerview()
        setupMainViewModel(mainAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.setting -> { val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)}

            R.id.favorite -> { val intent = Intent(this,
                FavoriteActivity::class.java)
            startActivity(intent)}
        }


        return super.onOptionsItemSelected(item)
    }

    private fun setupMainViewModel(mainAdapter : MainAdapter){
        mainViewModel.getUsers().observe(this, Observer { UserModel ->
            if (UserModel != null){
                mainAdapter.setData(UserModel)
                showLoading(false)
            }
        })
    }

    private fun setupRecyclerview(){
        mainAdapter.notifyDataSetChanged()
        rv_user.setHasFixedSize(true)
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.adapter = mainAdapter

        mainAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                val intent = Intent(this@MainActivity,
                    DetailActivity::class.java)
                intent.putExtra(Componen.USERNAME,data.username)
                startActivity(intent)
            }
        })
    }

    private fun searchUsers(){
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()){
                    mainAdapter.listUser.clear()
                    setupRecyclerview()
                    mainViewModel.searchUser(query)
                    showLoading(true)

                }else{
                    return true
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return false
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

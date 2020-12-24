package com.naifla.submissiongithubuser.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.naifla.submissiongithubuser.model.Favorite
import com.naifla.submissiongithubuser.viewmodel.FavoriteViewModel
import com.naifla.submissiongithubuser.R
import com.naifla.submissiongithubuser.adapter.SectionPagerAdapter
import com.naifla.submissiongithubuser.model.UserModel
import com.naifla.submissiongithubuser.utility.Componen
import com.naifla.submissiongithubuser.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel : DetailViewModel
    private lateinit var favoriteViewModel : FavoriteViewModel
    private var dataUser =UserModel()

    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val getIntent = intent.getStringExtra(Componen.USERNAME)

        detailViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)

        favoriteViewModel = ViewModelProvider(this)
            .get(FavoriteViewModel::class.java)

        showLoading(true)
        setupActionBar()
        setupViewPager(getIntent)
        setUser(getIntent)
        statusFavorite(getIntent)

        fab_favorite.setOnClickListener {

            insertFavorite(it)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu_setting,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting){
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager(username : String){
        val sectionsPagerAdapter = SectionPagerAdapter(this,supportFragmentManager)
        sectionsPagerAdapter.username = username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }

    private fun setupActionBar(){
        if (supportActionBar != null){
            supportActionBar?.title = "Detail User Github"
        }
    }

    private fun setUser (username : String){
        showLoading(true)
        detailViewModel.setUser(username,this)

        detailViewModel.getListUser().observe(this, Observer { UserModel ->
            if (UserModel != null){
                dataUser = UserModel.get(0)
                tv_username.text = dataUser.username
                tv_company.text = dataUser.company
                tv_addres.text = dataUser.location
                tv_followed.text = dataUser.followers.toString()
                tv_following.text = dataUser.following.toString()
                tv_repository.text = dataUser.repos.toString()
                Glide.with(this)
                    .load(dataUser.avatar)
                    .into(img_avatar)

                showLoading(false)
            }

        })
    }

    private fun insertFavorite(it : View){
        val name = dataUser.username.toString()
        val company = dataUser.company.toString()
        val address = dataUser.location.toString()
        val followers = dataUser.followers
        val following = dataUser.following
        val repos = dataUser.repos
        val avatar = dataUser.avatar.toString()

        if (statusFavorite){

            favoriteViewModel.deleteFavorite(tv_username.text.toString())

            Snackbar.make(it,"Data Deleted ",Snackbar.LENGTH_SHORT)
                .setAction("Action",null)
                .show()

            iconFavorite(false)

        }else{
            favoriteViewModel.insertFavorite(
                Favorite(
                    name = name, company = company,
                    address = address, repos = repos,
                    following = following, followers = followers,
                    avatar = avatar
                )
            )

            Snackbar.make(it,"Data Inserted ",Snackbar.LENGTH_SHORT)
                .setAction("Action",null)
                .show()
            iconFavorite(true)

        }
    }

    private fun iconFavorite(boolean: Boolean){
        if (boolean){
            statusFavorite = true
            fab_favorite.setImageResource(R.drawable.ic_favorite_white_24dp)
        }else{
            statusFavorite = false
            fab_favorite.setImageResource(R.drawable.ic_favorite_border_white_24dp)
        }
    }


    private fun showLoading (state : Boolean){
        if (state){
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE
        }
    }

    private fun statusFavorite(name :String?){
        favoriteViewModel.getFavoriteByName(name!!)?.
            observe(this, Observer {
                if (it.isNotEmpty()){
                    iconFavorite(true)
                }else{
                    iconFavorite(false)
                }

            })
    }


}

package com.naifla.submissiongithubuser.model

import android.app.Application
import androidx.lifecycle.LiveData
import com.naifla.submissiongithubuser.room.FavoriteDao
import com.naifla.submissiongithubuser.room.FavoriteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FavoriteRepository (application : Application) {

    private var favorites: LiveData<List<Favorite>>? = null
    private val favoriteDao: FavoriteDao?

    init {
        val db =
            FavoriteRoomDatabase.getInstance(application.applicationContext)
        favoriteDao = db?.favoriteDao()
        favorites = favoriteDao?.getFavorite()
    }

    fun getFavorite() : LiveData<List<Favorite>>?{
        return favorites
    }

    fun getFavoriteByName(name : String) : LiveData<List<Favorite>>?{
        return favoriteDao?.getFavoriteByName(name)
    }

    fun insertFavorite(favorite: Favorite) = runBlocking{
        this.launch (Dispatchers.IO) {
            favoriteDao?.insertFavorite(favorite)
        }
    }

    fun deleteFavorite(name : String){
        runBlocking {
            this.launch (Dispatchers.IO){
                favoriteDao?.deleteFavorite(name)
            }
        }
    }

}
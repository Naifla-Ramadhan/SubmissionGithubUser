package com.naifla.submissiongithubuser.viewmodel

import android.app.Application

import androidx.lifecycle.*
import com.naifla.submissiongithubuser.model.Favorite
import com.naifla.submissiongithubuser.model.FavoriteRepository


class FavoriteViewModel (application: Application) : AndroidViewModel(application){


    private var favoriteRepository =
        FavoriteRepository(application)
    private var favorites: LiveData<List<Favorite>>? = favoriteRepository.getFavorite()

    fun insertFavorite(favorite: Favorite){
        favoriteRepository.insertFavorite(favorite)
    }

    fun getFavorite(): LiveData<List<Favorite>>?{
        return favorites
    }

    fun getFavoriteByName(name : String): LiveData<List<Favorite>>?{
        return favoriteRepository.getFavoriteByName(name)
    }

    fun deleteFavorite(name: String){
        favoriteRepository.deleteFavorite(name)
    }

}
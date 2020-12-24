package com.naifla.submissiongithubuser.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.naifla.submissiongithubuser.model.Favorite

@Dao
interface FavoriteDao{

    @Query("SELECT *FROM favorite_table ORDER BY name ASC")
    fun getFavorite(): LiveData<List<Favorite>>

    @Query("SELECT *FROM favorite_table WHERE name = :name")
    fun getFavoriteByName(name : String): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(vararg favorite : Favorite)

    @Query ("DELETE FROM favorite_table WHERE name = :name")
    suspend fun deleteFavorite(name : String)
}
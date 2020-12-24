package com.naifla.submissiongithubuser.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
class Favorite(

    @ColumnInfo(name = "name")
    var name : String,
    @ColumnInfo(name = "company")
    var company : String,
    @ColumnInfo(name = "address")
    var address : String,
    @ColumnInfo(name = "repos")
    var repos : Int?,
    @ColumnInfo(name = "following")
    var following : Int?,
    @ColumnInfo(name = "followers")
    var followers : Int?,
    @ColumnInfo(name = "avatar")
    var avatar: String

){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int? = null
}
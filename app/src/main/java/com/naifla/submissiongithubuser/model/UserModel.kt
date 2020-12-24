package com.naifla.submissiongithubuser.model

import com.google.gson.annotations.SerializedName


data class UserModel(
    @SerializedName("id")
    val id : Int?= null,
    @SerializedName("name")
    val name : String?=null,
    @SerializedName("login")
    var username : String?=null,
    @SerializedName("organizations_url")
    var organization : String?=null,
    @SerializedName("company")
    var company : String?=null,
    @SerializedName("location")
    var location : String?=null,
    @SerializedName("email")
    val email : String?=null,
    @SerializedName("follower")
    var followers : Int?=null,
    @SerializedName("following")
    var following : Int?=null,
    @SerializedName("public_repos")
    var repos : Int?=null,
    @SerializedName("avatar_url")
    var avatar : String?=null
)
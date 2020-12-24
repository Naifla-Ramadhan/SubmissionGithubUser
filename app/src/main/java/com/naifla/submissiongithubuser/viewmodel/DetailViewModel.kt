package com.naifla.submissiongithubuser.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.naifla.submissiongithubuser.model.UserModel
import com.naifla.submissiongithubuser.utility.Componen
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class DetailViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<UserModel>>()
    val TAG = javaClass.simpleName
    val itemUser = ArrayList<UserModel>()

    fun getListUser (): LiveData<ArrayList<UserModel>>{
        return  listUser
    }

    fun setUser(username : String,contex : Context){

        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", Componen.TOKEN)
        httpClient.addHeader("User-Agent", "request")
        val urlClient = Componen.USER_API + username

        httpClient.get(urlClient, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val dataUser = UserModel()
                    dataUser.username = jsonObject.getString("login")
                    dataUser.company = jsonObject.getString("company")
                    dataUser.location = jsonObject.getString("location")
                    dataUser.followers = jsonObject.getInt("followers")
                    dataUser.following = jsonObject.getInt("following")
                    dataUser.repos = jsonObject.getInt("public_repos")
                    dataUser.avatar= jsonObject.getString("avatar_url")

                    itemUser.clear()
                    itemUser.add(dataUser)

                }catch (e:Exception){
                    Log.d(TAG, e.message)
                }
                listUser.postValue(itemUser)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(contex, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun setFollower (username: String?){

        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization",Componen.TOKEN)
        httpClient.addHeader("User-Agent", "request")
        val urlClient = Componen.USER_API + username + "/followers"

        httpClient.get(urlClient,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                val items = JSONArray(result)
                Log.d(TAG, "response : $result")
                try {
                    for (i in 0 until items.length()){
                        val jsonObject = items.getJSONObject(i)
                        val dataFollowers = UserModel()
                        dataFollowers.username = jsonObject.getString("login")
                        dataFollowers.avatar = jsonObject.getString("avatar_url")

                            itemUser.add(dataFollowers)
                    }
                }catch (e : Exception){
                    Log.d(TAG, e.message)
                }
                Log.d(TAG, "responseItem : $itemUser")
                listUser.postValue(itemUser)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d(TAG, errorMessage)
            }
        })
    }

    fun setFollowing (username: String ?){

        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization",Componen.TOKEN)
        httpClient.addHeader("User-Agent", "request")
        val urlClient = Componen.USER_API + username + "/followers"

        httpClient.get(urlClient,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                val items = JSONArray(result)

                try {
                    for (i in 0 until items.length()){
                        val jsonObject = items.getJSONObject(i)
                        val dataFollowers = UserModel()
                        dataFollowers.username = jsonObject.getString("login")
                        dataFollowers.avatar = jsonObject.getString("avatar_url")

                        itemUser.clear()
                        itemUser.add(dataFollowers)

                    }
                }catch (e : Exception){
                    Log.d(TAG, e.message)
                }

                listUser.postValue(itemUser)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d(TAG, errorMessage)
            }
        })
    }
}
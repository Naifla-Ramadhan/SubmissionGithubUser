package com.naifla.submissiongithubuser.viewmodel
import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.naifla.submissiongithubuser.model.UserModel
import com.naifla.submissiongithubuser.utility.Componen
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel(){

    val listUser = MutableLiveData<ArrayList<UserModel>>()
    var query : String? = null

    fun getUsers() : LiveData<ArrayList<UserModel>>{
        return listUser
    }

    fun searchUser(username : String){

        val listItems = ArrayList<UserModel>()

        val searchClient = AsyncHttpClient()
        searchClient.addHeader("Authorization", Componen.TOKEN)
        searchClient.addHeader("User-Agent", "request")
        val urlClient = Componen.SEARCH_API + username

        searchClient.get(urlClient,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                listItems.clear()
                try {
                    val result = String(responseBody)
                    val responObject = JSONObject(result)
                    val items = responObject.getJSONArray("items")
                    for (i in 0 until items.length()){
                        val user = items.getJSONObject(i)
                        val userModel = UserModel()
                        userModel.username = user.getString("login")
                        userModel.company = user.getString("organizations_url")
                        userModel.avatar = user.getString("avatar_url")

                        listItems.add(userModel)
                    }
                    listUser.postValue(listItems)

                }catch (e:Exception){
                    Log.d("onCatch", e.message.toString())
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })

    }



}




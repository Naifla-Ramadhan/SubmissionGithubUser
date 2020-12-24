package com.naifla.submissiongithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.naifla.submissiongithubuser.R
import com.naifla.submissiongithubuser.model.UserModel
import kotlinx.android.synthetic.main.list_item_user.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    private var onItemClickCallback : OnItemClickCallback? = null
    val listUser = ArrayList<UserModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<UserModel>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(listUser[position])

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user : UserModel){
            with(itemView){
                tv_item_username.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(55,55))
                    .into(img_item_photo)

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(user)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data : UserModel)
    }

}
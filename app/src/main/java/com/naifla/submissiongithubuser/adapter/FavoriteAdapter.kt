package com.naifla.submissiongithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.naifla.submissiongithubuser.model.Favorite
import com.naifla.submissiongithubuser.R
import kotlinx.android.synthetic.main.list_item_user.view.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    val listFavorite = ArrayList<Favorite>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: List<Favorite>) {
        listFavorite.clear()
        listFavorite.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listFavorite.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(listFavorite[position])

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(favorite: Favorite) {
            with(itemView) {
                tv_item_username.text = favorite.name
                Glide.with(itemView.context)
                    .load(favorite.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(img_item_photo)

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(favorite)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorite)
    }
}
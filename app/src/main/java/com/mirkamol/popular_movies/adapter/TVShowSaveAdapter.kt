package com.mirkamol.popular_movies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirkamol.popular_movies.model.TVShow
import com.mirkamol.popular_movies.R
import com.mirkamol.popular_movies.databinding.ItemTvShowBinding
import com.mirkamol.popular_movies.fragment.save.SaveFragment

class TVShowSaveAdapter(var homeFragment: SaveFragment, var items: ArrayList<TVShow>) :
    BaseAdapter() {


    @SuppressLint("NotifyDataSetChanged")
    fun setViewTVShows(tvShows: List<TVShow>) {
        // items.clear()
        items.addAll(tvShows)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow: TVShow = items[position]
        if (holder is TVShowViewHolder) {
            Glide.with(homeFragment).load(tvShow.image_thumbnail_path).into(holder.binding.ivMovie)
            holder.binding.tvName.text = tvShow.name
            holder.binding.tvType.text = tvShow.network

            ViewCompat.setTransitionName(holder.binding.ivMovie, tvShow.name)
            //Click the tv show


        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class TVShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTvShowBinding.bind(view)
    }
}
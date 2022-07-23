package com.mirkamol.popular_movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirkamol.popular_movies.R
import com.mirkamol.popular_movies.databinding.ItemTvEpisodeBinding
import com.mirkamol.popular_movies.model.Episode

class TVEpisodeAdapter(val items: List<Episode>) : BaseAdapter() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvEpisode = items[position]
        if (holder is TVEpisodeViewHolder){
            holder.binding.tvSeason.text = tvEpisode.season.toString()
            holder.binding.tvEpisode.text = tvEpisode.episode.toString()
            holder.binding.tvName.text = tvEpisode.name
            holder.binding.tvData.text = tvEpisode.airDate
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_episode, parent, false)
        return TVEpisodeViewHolder(view)
    }

    inner class TVEpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTvEpisodeBinding.bind(view)
    }

}
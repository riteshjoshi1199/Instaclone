package com.ritesh.instaclone.ui.adepters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.R
import com.example.instaclone.databinding.ReelDesignBinding
import com.ritesh.instaclone.data.models.Reel
import com.squareup.picasso.Picasso

class ReelAdapter(private var reelList: ArrayList<Reel>): RecyclerView.Adapter<ReelAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ReelDesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReelDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList[position].profileLink)
            .placeholder(R.drawable.user)
            .into(holder.binding.profileImage)
        holder.binding.caption.text = reelList[position].caption
        holder.binding.videoView.setVideoPath(reelList[position].reelUrl)

        holder.binding.videoView.setOnPreparedListener {
            holder.binding.progressBar.visibility = View.GONE
            holder.binding.videoView.apply {
                it.isLooping = true
            }.start()
        }
    }
}
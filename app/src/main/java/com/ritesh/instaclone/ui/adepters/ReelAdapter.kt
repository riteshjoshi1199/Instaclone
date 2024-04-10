package com.ritesh.instaclone.ui.adepters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.R
import com.example.instaclone.databinding.AllReelRvItemBinding
import com.ritesh.instaclone.data.models.ReelModel
import com.squareup.picasso.Picasso

class ReelAdapter(private var reelModelList: ArrayList<ReelModel>): RecyclerView.Adapter<ReelAdapter.AllReelItemViewHolder>() {

    inner class AllReelItemViewHolder(val binding: AllReelRvItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllReelItemViewHolder {
        val binding = AllReelRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllReelItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelModelList.size
    }

    override fun onBindViewHolder(holder: AllReelItemViewHolder, position: Int) {
        Picasso.get().load(reelModelList[position].profileLink)
            .placeholder(R.drawable.user)
            .into(holder.binding.profileImage)
        holder.binding.caption.text = reelModelList[position].caption
        holder.binding.videoView.setVideoPath(reelModelList[position].reelUrl)

        holder.binding.videoView.setOnPreparedListener {
            holder.binding.progressBar.visibility = View.GONE
            holder.binding.videoView.apply {
                it.isLooping = true
            }.start()
        }
    }
}
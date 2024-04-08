package com.ritesh.instaclone.ui.adepters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instaclone.R
import com.example.instaclone.databinding.FollowRvBinding
import com.ritesh.instaclone.data.models.User

class FollowAdapter(private var followList: ArrayList<User>): RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: FollowRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FollowRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = followList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.binding.storyimage.context)
            .load(followList[position].image)
            .placeholder(R.drawable.user)
            .into(holder.binding.storyimage)

        holder.binding.name.text = followList[position].name
    }
}
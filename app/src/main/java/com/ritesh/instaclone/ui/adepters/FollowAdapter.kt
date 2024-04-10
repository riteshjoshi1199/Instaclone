package com.ritesh.instaclone.ui.adepters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instaclone.R
import com.example.instaclone.databinding.FollowRvItemBinding
import com.ritesh.instaclone.data.models.MyUserModel

class FollowAdapter(private var followList: ArrayList<MyUserModel>): RecyclerView.Adapter<FollowAdapter.FollowItemViewHolder>() {

    inner class FollowItemViewHolder(val binding: FollowRvItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowItemViewHolder {
        val binding = FollowRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowItemViewHolder(binding)
    }

    override fun getItemCount(): Int = followList.size

    override fun onBindViewHolder(holder: FollowItemViewHolder, position: Int) {

        Glide.with(holder.binding.storyimage.context)
            .load(followList[position].image)
            .placeholder(R.drawable.user)
            .into(holder.binding.storyimage)

        holder.binding.name.text = followList[position].name
    }
}
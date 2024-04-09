package com.ritesh.instaclone.ui.adepters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.databinding.MyPostRecyclerviewDesignBinding
import com.ritesh.instaclone.data.models.Post
import com.squareup.picasso.Picasso

class MyPostRecyclerViewAdapter(private var postList: ArrayList<Post>): RecyclerView.Adapter<MyPostRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: MyPostRecyclerviewDesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyPostRecyclerviewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(postList[position].postUrl).into(holder.binding.PostImage)
    }

}

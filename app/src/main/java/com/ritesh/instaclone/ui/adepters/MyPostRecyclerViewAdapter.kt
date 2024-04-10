package com.ritesh.instaclone.ui.adepters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.databinding.MyPostRvItemBinding
import com.ritesh.instaclone.data.models.PostModel
import com.squareup.picasso.Picasso

class MyPostRecyclerViewAdapter(private var postModelList: ArrayList<PostModel>): RecyclerView.Adapter<MyPostRecyclerViewAdapter.MyPostItemViewHolder>() {
    inner class MyPostItemViewHolder(var binding: MyPostRvItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostItemViewHolder {
        val binding = MyPostRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPostItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postModelList.size
    }

    override fun onBindViewHolder(holder: MyPostItemViewHolder, position: Int) {
        Picasso.get().load(postModelList[position].postUrl).into(holder.binding.PostImage)
    }

}

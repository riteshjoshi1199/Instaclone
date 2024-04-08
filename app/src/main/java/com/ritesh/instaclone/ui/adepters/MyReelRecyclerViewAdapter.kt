package com.ritesh.instaclone.ui.adepters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instaclone.databinding.MyReelRecyclerviewDesignBinding
import com.ritesh.instaclone.data.models.Reel

class MyReelRecyclerViewAdapter(var context: Context, var reelList: ArrayList<Reel>) :
    RecyclerView.Adapter<MyReelRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: MyReelRecyclerviewDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MyReelRecyclerviewDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(reelList.get(position).reelUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.binding.PostReel)

    }

}

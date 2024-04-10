package com.ritesh.instaclone.ui.adepters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instaclone.databinding.MyReelRvItemBinding
import com.ritesh.instaclone.data.models.ReelModel

class MyReelRecyclerViewAdapter(private var reelModelList: ArrayList<ReelModel>): RecyclerView.Adapter<MyReelRecyclerViewAdapter.MyReelItemViewHolder>() {
    inner class MyReelItemViewHolder(var binding: MyReelRvItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReelItemViewHolder {
        val binding = MyReelRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyReelItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelModelList.size
    }

    override fun onBindViewHolder(holder: MyReelItemViewHolder, position: Int) {
        Glide.with(holder.binding.postReel.context)
            .load(reelModelList[position].reelUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.postReel)

    }

}

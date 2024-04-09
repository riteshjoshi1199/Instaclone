package com.ritesh.instaclone.ui.adepters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instaclone.R
import com.example.instaclone.databinding.PostRecyclerviewDesignBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ritesh.instaclone.data.models.Post
import com.ritesh.instaclone.data.models.User
import com.ritesh.instaclone.data.utils.USER_NODE


class PostAdapter(private var postList: ArrayList<Post>): RecyclerView.Adapter<PostAdapter.MyHolder>() {

    inner class MyHolder(var binding: PostRecyclerviewDesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = PostRecyclerviewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            val userId = postList[position].uid
            Firebase.firestore.collection(USER_NODE).document(userId).get()
                .addOnSuccessListener {
                    val user: User? = it.toObject<User?>()
                    user?.let { u ->
                        Glide.with(holder.binding.profileImage)
                            .load(u.image).placeholder(R.drawable.user)
                            .into(holder.binding.profileImage)

                        holder.binding.name.text = u.name
                    } ?: run {
                        Log.e(TAG, "onBindViewHolder: user is not found")
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "onBindViewHolder: Exception", e)
        }


        Glide.with(holder.binding.postImage)
            .load(postList[position].postUrl)
            .placeholder(R.drawable.loading)
            .into(holder.binding.postImage)

        try {
            val text = TimeAgo.using(postList[position].time.toLong())
            holder.binding.time.text = text
        } catch (e: Exception) {
            holder.binding.time.text = null
        }


        holder.binding.share.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT, postList[position].postUrl)
            holder.binding.root.context.startActivity(i)
        }

        holder.binding.caption.text = postList[position].caption

        holder.binding.like.setOnClickListener {
            holder.binding.like.setImageResource(R.drawable.redheart)
        }

    }

    companion object {
        private const val TAG = "PostAdapter"
    }
}
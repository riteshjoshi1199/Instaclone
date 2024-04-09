package com.ritesh.instaclone.ui.adepters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.R
import com.example.instaclone.databinding.SearchRvBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.ritesh.instaclone.data.models.User
import com.ritesh.instaclone.data.utils.FOLLOW
import com.squareup.picasso.Picasso

class SearchAdapter(private var userList: ArrayList<User>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: SearchRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isfollow = false

        Picasso.get().load(userList[position].image)
            .placeholder(R.drawable.user)
            .into(holder.binding.profileImage)

        holder.binding.name.text = userList[position].name

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
            .whereEqualTo("email", userList[position].email).get().addOnSuccessListener {
                if (it.documents.size == 0) {
                    isfollow = false
                } else {
                    holder.binding.followBt.text = "Unfollow"
                    isfollow = true
                }
            }

        holder.binding.followBt.setOnClickListener {
            if (isfollow) {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
                    .whereEqualTo("email", userList[position].email).get().addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document(it.documents[0].id).delete()
                        holder.binding.followBt.text = "Follow"
                        isfollow = false
                    }

            } else {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document().set(userList[position])
                holder.binding.followBt.text = "Unfollow"
                isfollow = true
            }
        }
    }
}
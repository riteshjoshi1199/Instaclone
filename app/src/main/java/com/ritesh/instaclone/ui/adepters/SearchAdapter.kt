package com.ritesh.instaclone.ui.adepters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.R
import com.example.instaclone.databinding.SearchRvItemBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.ritesh.instaclone.data.models.MyUserModel
import com.ritesh.instaclone.data.utils.FOLLOW
import com.squareup.picasso.Picasso

class SearchAdapter(private var myUserModelList: ArrayList<MyUserModel>): RecyclerView.Adapter<SearchAdapter.SearchItemViewHolder>() {
    inner class SearchItemViewHolder(var binding: SearchRvItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = SearchRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return myUserModelList.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        var isfollow = false

        Picasso.get().load(myUserModelList[position].image)
            .placeholder(R.drawable.user)
            .into(holder.binding.profileImage)

        holder.binding.name.text = myUserModelList[position].name

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
            .whereEqualTo("email", myUserModelList[position].email).get().addOnSuccessListener {
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
                    .whereEqualTo("email", myUserModelList[position].email).get().addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document(it.documents[0].id).delete()
                        holder.binding.followBt.text = "Follow"
                        isfollow = false
                    }

            } else {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document().set(myUserModelList[position])
                holder.binding.followBt.text = "Unfollow"
                isfollow = true
            }
        }
    }
}
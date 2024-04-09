package com.ritesh.instaclone.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instaclone.databinding.FragmentMyPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ritesh.instaclone.data.models.Post
import com.ritesh.instaclone.ui.adepters.MyPostRecyclerViewAdapter

class MyPostFragment: Fragment() {
    private lateinit var binding: FragmentMyPostBinding
    private val postList = ArrayList<Post>()
    private val adepter: MyPostRecyclerViewAdapter by lazy {
        MyPostRecyclerViewAdapter(postList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyPostBinding.inflate(inflater, container, false)
        binding.myRecyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.myRecyclerView.adapter = adepter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            val tempList = arrayListOf<Post>()
            for (i in it.documents) {
                val post: Post = i.toObject<Post>()!!
                tempList.add(post)
            }

            postList.addAll(tempList)
            adepter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }
}
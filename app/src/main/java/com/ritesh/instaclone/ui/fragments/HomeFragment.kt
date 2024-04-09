package com.ritesh.instaclone.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instaclone.R
import com.example.instaclone.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ritesh.instaclone.data.models.Post
import com.ritesh.instaclone.data.models.User
import com.ritesh.instaclone.data.utils.FOLLOW
import com.ritesh.instaclone.data.utils.POST
import com.ritesh.instaclone.ui.adepters.FollowAdapter
import com.ritesh.instaclone.ui.adepters.PostAdapter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private var followList = ArrayList<User>()
    private val followAdapter: FollowAdapter by lazy {
        FollowAdapter(followList)
    }

    private var postList = ArrayList<Post>()
    private val postAdapter: PostAdapter by lazy {
        PostAdapter(postList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.followRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.followRv.adapter = followAdapter

        binding.homerv.layoutManager = LinearLayoutManager(requireContext())
        binding.homerv.adapter = postAdapter

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_manu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMyFollowers()
        setupAllPostFeed()
    }


    private fun setupMyFollowers() {
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).get()
            .addOnSuccessListener {
                val tempList = ArrayList<User>()
                for (i in it.documents) {
                    val user: User = i.toObject<User>()!!
                    tempList.add(user)
                }
                followList.addAll(tempList)
                followAdapter.notifyDataSetChanged()
            }

    }

    private fun setupAllPostFeed() {
        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            val tempList = ArrayList<Post>()
            postList.clear()

            for (i in it.documents) {
                val post: Post = i.toObject<Post>()!!
                tempList.add(post)
            }

            postList.addAll(tempList)
            postAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}
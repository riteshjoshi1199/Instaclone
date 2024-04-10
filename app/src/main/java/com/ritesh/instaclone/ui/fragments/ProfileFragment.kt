package com.ritesh.instaclone.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instaclone.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ritesh.instaclone.data.models.User
import com.ritesh.instaclone.data.utils.USER_NODE
import com.ritesh.instaclone.ui.activities.SignupActivity
import com.ritesh.instaclone.ui.adepters.ViewPagerAdepter
import com.squareup.picasso.Picasso

class ProfileFragment: Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewPagerAdepter: ViewPagerAdepter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewPagerAdepter = ViewPagerAdepter(requireActivity().supportFragmentManager)
        viewPagerAdepter.addFragments(MyPostFragment(), "Post")
        viewPagerAdepter.addFragments(MyReelsFragment(), "Reels")

        binding.viewPager.adapter = viewPagerAdepter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.EditProfileButton.setOnClickListener {
            val intent = Intent(activity, SignupActivity::class.java)
            intent.putExtra("MODE", 1)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }


    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            val user: User = it.toObject<User>()!!
            binding.name.text = user.name
            binding.bio.text = user.email
            if (!user.image.isNullOrEmpty()) {
                Picasso.get().load(user.image).into(binding.profileImage)
            }
        }
    }
}
package com.ritesh.instaclone.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instaclone.databinding.FragmentMyProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ritesh.instaclone.data.models.MyUserModel
import com.ritesh.instaclone.data.utils.USER_NODE
import com.ritesh.instaclone.ui.activities.SignupActivity
import com.ritesh.instaclone.ui.adepters.ViewPagerAdepter
import com.squareup.picasso.Picasso

class MyProfileFragment: Fragment() {
    private lateinit var binding: FragmentMyProfileBinding
    private lateinit var viewPagerAdepter: ViewPagerAdepter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyProfileBinding.inflate(inflater, container, false)
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
            intent.putExtra("MODE", SignupActivity.MODE_PROFILE_EDIT)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }


    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            val myUserModel: MyUserModel = it.toObject<MyUserModel>()!!
            binding.name.text = myUserModel.name
            binding.bio.text = myUserModel.email
            if (!myUserModel.image.isNullOrEmpty()) {
                Picasso.get().load(myUserModel.image).into(binding.profileImage)
            }
        }
    }
}
package com.ritesh.instaclone.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instaclone.databinding.FragmentAddContentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ritesh.instaclone.ui.activities.AddPostActivity
import com.ritesh.instaclone.ui.activities.AddReelActivity

class AddContentFragment: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddContentBinding.inflate(inflater, container, false)

        binding.post.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), AddPostActivity::class.java))
            activity?.finish()
        }

        binding.reel.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), AddReelActivity::class.java))
            activity?.finish()

        }
        return binding.root
    }

    companion object {

    }
}
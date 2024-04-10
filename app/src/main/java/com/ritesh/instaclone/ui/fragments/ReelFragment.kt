package com.ritesh.instaclone.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instaclone.databinding.FragmentReelBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ritesh.instaclone.data.models.ReelModel
import com.ritesh.instaclone.data.utils.REEL
import com.ritesh.instaclone.ui.adepters.ReelAdapter

class ReelFragment: Fragment() {
    private lateinit var binding: FragmentReelBinding
    private var reelModelList = ArrayList<ReelModel>()
    private val reelAdapter: ReelAdapter by lazy {
        ReelAdapter(reelModelList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReelBinding.inflate(inflater, container, false)
        binding.viewPager.adapter = reelAdapter
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            val tempList = ArrayList<ReelModel>()
            reelModelList.clear()
            for (i in it.documents) {
                val reelModel = i.toObject<ReelModel>()!!
                tempList.add(reelModel)
            }
            reelModelList.addAll(tempList)
            reelModelList.reverse()
            reelAdapter.notifyDataSetChanged()
        }
    }
}
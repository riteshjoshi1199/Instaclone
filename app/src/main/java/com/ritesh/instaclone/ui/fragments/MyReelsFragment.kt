package com.ritesh.instaclone.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instaclone.databinding.FragmentMyReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ritesh.instaclone.data.models.Reel
import com.ritesh.instaclone.data.utils.REEL
import com.ritesh.instaclone.ui.adepters.MyReelRecyclerViewAdapter

class MyReelsFragment: Fragment() {
    private lateinit var binding: FragmentMyReelsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyReelsBinding.inflate(inflater, container, false)
        var reelList = ArrayList<Reel>()
        var adepter = MyReelRecyclerViewAdapter(requireContext(), reelList)
        binding.MyRecyclerView.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.MyRecyclerView.adapter = adepter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).get().addOnSuccessListener {
            var tempList = arrayListOf<Reel>()
            for (i in it.documents) {
                var reel: Reel = i.toObject<Reel>()!!
                tempList.add(reel)

            }
            reelList.addAll(tempList)
            adepter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }
}

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
    private var reelList = ArrayList<Reel>()
    private val myReelRecyclerViewAdapter: MyReelRecyclerViewAdapter by lazy {
        MyReelRecyclerViewAdapter(reelList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyReelsBinding.inflate(inflater, container, false)
        binding.myRecyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.myRecyclerView.adapter = myReelRecyclerViewAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).get().addOnSuccessListener {
            val tempList = arrayListOf<Reel>()
            for (i in it.documents) {
                val reel: Reel = i.toObject<Reel>()!!
                tempList.add(reel)

            }
            reelList.addAll(tempList)
            myReelRecyclerViewAdapter.notifyDataSetChanged()
        }
    }

}

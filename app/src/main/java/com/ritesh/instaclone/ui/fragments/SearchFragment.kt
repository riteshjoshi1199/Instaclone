package com.ritesh.instaclone.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instaclone.databinding.FragmentSearchBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ritesh.instaclone.data.models.MyUserModel
import com.ritesh.instaclone.data.utils.USER_NODE
import com.ritesh.instaclone.ui.adepters.SearchAdapter


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private var myUserModelList = ArrayList<MyUserModel>()
    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(myUserModelList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.srv.layoutManager = LinearLayoutManager(requireContext())
        binding.srv.adapter = searchAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            val tempList = ArrayList<MyUserModel>()
            myUserModelList.clear()

            if (it.documents.isNotEmpty()) {
                for (i in it.documents) {
                    if (i.id != Firebase.auth.currentUser!!.uid) {
                        val myUserModel: MyUserModel = i.toObject<MyUserModel>()!!
                        tempList.add(myUserModel)
                    }
                }

                myUserModelList.addAll(tempList)
                searchAdapter.notifyDataSetChanged()
            } else {
                //do nothing
            }
        }

        binding.searchButton.setOnClickListener {
            val searchTerm = binding.searchView.text?.toString()?.trim()
            if (searchTerm.isNullOrEmpty() || searchTerm.isBlank()){
                Toast.makeText(requireContext(), "Please input something to search", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Firebase.firestore.collection(USER_NODE).whereEqualTo("name", searchTerm).get().addOnSuccessListener {
                    val tempList = ArrayList<MyUserModel>()
                    myUserModelList.clear()
                    for (i in it.documents) {
                        if (i.id != Firebase.auth.currentUser!!.uid) {
                            val myUserModel: MyUserModel = i.toObject<MyUserModel>()!!
                            tempList.add(myUserModel)
                        }
                    }

                    myUserModelList.addAll(tempList)
                    searchAdapter.notifyDataSetChanged()
                }
        }
    }


    companion object {
        private const val TAG = "SearchFragment"
    }
}
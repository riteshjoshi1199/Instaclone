package com.ritesh.instaclone.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instaclone.databinding.ActivityPostsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ritesh.instaclone.data.models.Post
import com.ritesh.instaclone.data.models.User
import com.ritesh.instaclone.data.utils.POST
import com.ritesh.instaclone.data.utils.POST_FOLDER
import com.ritesh.instaclone.data.utils.USER_NODE
import com.ritesh.instaclone.data.utils.uploadImage


class PostsActivity: AppCompatActivity() {
    val binding by lazy {
        ActivityPostsBinding.inflate(layoutInflater)
    }

    var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) { url ->
                if (url != null) {
                    binding.selectimage.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostsActivity, HomeActivity::class.java))
            finish()
        }
        binding.selectimage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@PostsActivity, HomeActivity::class.java))
            finish()
        }
        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user: User? = it.toObject<User?>()

                    val post: Post = Post(
                        imageUrl!!,
                        caption = binding.caption.editText?.text.toString(),
                        uid = Firebase.auth.currentUser!!.uid,
                        time = System.currentTimeMillis().toString()
                    )

                    Firebase.firestore.collection(POST).document().set(post)
                        .addOnSuccessListener {
                            Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
                                .document()
                                .set(post)
                                .addOnSuccessListener {
                                    startActivity(
                                        Intent(
                                            this@PostsActivity,
                                            HomeActivity::class.java
                                        )
                                    )
                                    finish()

                                }
                        }
                }
        }
    }
}


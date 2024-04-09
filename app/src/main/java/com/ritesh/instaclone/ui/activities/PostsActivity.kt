package com.ritesh.instaclone.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instaclone.databinding.ActivityPostsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.ritesh.instaclone.data.models.Post
import com.ritesh.instaclone.data.utils.POST
import com.ritesh.instaclone.data.utils.POST_FOLDER
import com.ritesh.instaclone.data.utils.uploadImage


class PostsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPostsBinding

    private var imageUrl: String? = null
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
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

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
            val post = Post(
                imageUrl!!,
                caption = binding.caption.editText?.text.toString(),
                uid = Firebase.auth.currentUser!!.uid,
                time = System.currentTimeMillis().toString()
            )

            Firebase.firestore.collection(POST).document().set(post)
            Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener {
                startActivity(Intent(this@PostsActivity, HomeActivity::class.java))
                Toast.makeText(this, "Post added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}


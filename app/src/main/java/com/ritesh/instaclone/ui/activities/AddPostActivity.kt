package com.ritesh.instaclone.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instaclone.databinding.ActivityAddPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.ritesh.instaclone.data.models.PostModel
import com.ritesh.instaclone.data.utils.POST
import com.ritesh.instaclone.data.utils.POST_FOLDER
import java.util.UUID


class AddPostActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding

    private fun uploadImage(uri: Uri, callback: (String?) -> Unit) {
        FirebaseStorage.getInstance().getReference(POST_FOLDER).child(UUID.randomUUID().toString())
            .putFile(uri).addOnSuccessListener { ref ->
                ref.storage.downloadUrl.addOnSuccessListener { url ->
                    val imageUrl = url.toString()
                    callback(imageUrl)
                }
            }
    }


    private var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri) { url ->
                if (url != null) {
                    binding.selectimage.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@AddPostActivity, HomeActivity::class.java))
            finish()
        }

        binding.selectimage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@AddPostActivity, HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            val postModel = PostModel(
                imageUrl!!,
                caption = binding.caption.editText?.text.toString(),
                uid = Firebase.auth.currentUser!!.uid,
                time = System.currentTimeMillis().toString()
            )

            Firebase.firestore.collection(POST).document().set(postModel)
            Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(postModel).addOnSuccessListener {
                startActivity(Intent(this@AddPostActivity, HomeActivity::class.java))
                Toast.makeText(this, "Post added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}


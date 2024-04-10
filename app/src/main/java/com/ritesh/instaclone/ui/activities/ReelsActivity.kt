package com.ritesh.instaclone.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.instaclone.databinding.ActivityReelsBinding
import com.example.instaclone.databinding.LayoutLoadingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.ritesh.instaclone.data.models.Reel
import com.ritesh.instaclone.data.models.User
import com.ritesh.instaclone.data.utils.REEL
import com.ritesh.instaclone.data.utils.REEL_FOLDER
import com.ritesh.instaclone.data.utils.USER_NODE
import java.util.UUID

class ReelsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityReelsBinding
    private lateinit var videoUrl: String

    private val progressDialogBuilder: MaterialAlertDialogBuilder by lazy {
        MaterialAlertDialogBuilder(this)
    }

    private fun uploadVideo(
        uri: Uri,
        progressDialogBuilder: MaterialAlertDialogBuilder,
        callback: (String?) -> Unit
    ) {
        val layoutLoadingBinding = LayoutLoadingBinding.inflate(layoutInflater)
        progressDialogBuilder.setView(layoutLoadingBinding.root)
        val progressDialog = progressDialogBuilder.create().apply { setCancelable(false) }
        progressDialog.show()

        FirebaseStorage.getInstance().getReference(REEL_FOLDER).child(UUID.randomUUID().toString())
            .putFile(uri).addOnProgressListener {
                val uploadedValue: Long = (it.bytesTransferred / it.totalByteCount) * 100
                layoutLoadingBinding.progressBar.progress = uploadedValue.toInt()
            }.addOnSuccessListener { ref ->
                ref.storage.downloadUrl.addOnSuccessListener { url ->
                    val videoUrl = url.toString()
                    progressDialog.dismiss()
                    callback(videoUrl)
                }
                Toast.makeText(this, "Reel uploaded", Toast.LENGTH_SHORT).show()
            }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, progressDialogBuilder) { url ->
                if (url != null) {
                    videoUrl = url
                    Glide.with(binding.selectReel.context).load(url).into(binding.selectReel)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                val user: User = it.toObject<User>()!!

                val reel: Reel = Reel(videoUrl, binding.caption.editText?.text.toString(), user.image!!)
                Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).document()
                        .set(reel).addOnSuccessListener {
                            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                            finish()
                        }
                }
            }
        }
    }

}
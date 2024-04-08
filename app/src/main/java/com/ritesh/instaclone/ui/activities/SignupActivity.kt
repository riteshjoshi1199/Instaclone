package com.ritesh.instaclone.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instaclone.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ritesh.instaclone.data.models.User
import com.ritesh.instaclone.data.utils.USER_NODE
import com.ritesh.instaclone.data.utils.USER_PROFILE_FOLDER
import com.ritesh.instaclone.data.utils.uploadImage
import com.squareup.picasso.Picasso

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var user: User

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it != null) {
                    user.image = it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo use movable action method for string.
        val text = "<font color=#FF000000>Already have an Account</font> <font color=#1E88E5>Login?</font>"
        binding.login.text = Html.fromHtml(text)
        binding.login.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            finish()
        }

        user = User()
        if (intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE", -1) == 1)
                binding.SignUpButton.text = "EditProfile"

            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    user = it.toObject<User>()!!
                    if (!user.image.isNullOrEmpty()) {
                        Picasso.get().load(user.image).into(binding.profileImage)
                    }
                    binding.name.editText?.setText(user.name)
                    binding.email.editText?.setText(user.email)
                    binding.password.editText?.setText(user.password)

                }
        }

        binding.SignUpButton.setOnClickListener {

            if (intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE", -1) == 1) {
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                            startActivity(Intent(this@SignupActivity, HomeActivity::class.java))
                            finish()
                        }
                }
            }


            val name = binding.name.editText?.text?.toString()?.trim()
            val email = binding.email.editText?.text?.toString()?.trim()
            val password = binding.password.editText?.text?.toString()?.trim()

            if (name.isNullOrEmpty() || name.isBlank() ||
                email.isNullOrEmpty() || email.isBlank() ||
                password.isNullOrEmpty() || password.isBlank()
            ) {
                Toast.makeText(
                    this@SignupActivity,
                    "Please fill the all Information",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (isValidEmail(email)) {
                Toast.makeText(
                    this@SignupActivity,
                    "Please enter a correct email",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password.length < 6) {
                Toast.makeText(
                    this@SignupActivity,
                    "Password must have at least 6 characters",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email,
                    password
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        user.name = name
                        user.email = email
                        user.password = password

                        Firebase.firestore.collection(USER_NODE)
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                startActivity(Intent(this@SignupActivity, HomeActivity::class.java))
                                finish()
                            }

                    } else {
                        Toast.makeText(
                            this@SignupActivity,
                            result.exception?.localizedMessage ?: "Something went wrong!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.addimageImage.setOnClickListener {
            //todo: add storage permission to read files
            launcher.launch("image/*")
        }
    }

    // ___@___.___
    private fun isValidEmail(email: String): Boolean {
        val pattern = android.util.Patterns.EMAIL_ADDRESS
        return email.isNotEmpty() && pattern.matcher(email).matches()
    }

}


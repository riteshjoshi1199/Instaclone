package com.ritesh.instaclone.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instaclone.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ritesh.instaclone.data.models.MyUserModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.LoginButton.setOnClickListener {
            val email = binding.email.editText?.text?.toString()?.trim()
            val password = binding.password.editText?.text?.toString()?.trim()

            if (email.isNullOrEmpty() || email.isBlank() ||
                password.isNullOrEmpty() || password.isBlank()
            ) {
                Toast.makeText(
                    this@LoginActivity,
                    "Please fill all the details",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val myUserModel = MyUserModel(email, password)

                Firebase.auth.signInWithEmailAndPassword(myUserModel.email!!, myUserModel.password!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                it.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}
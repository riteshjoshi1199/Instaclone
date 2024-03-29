package com.example.instaclone.utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import javax.security.auth.callback.Callback

fun uploadImage(uri: Uri, folerName: String,callback:(String?)->Unit){
    var imageurl:String?=null
    FirebaseStorage.getInstance().getReference(folerName).child(UUID.randomUUID().toString())
        .putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageurl=it.toString()
                callback(imageurl)
            }
        }
}
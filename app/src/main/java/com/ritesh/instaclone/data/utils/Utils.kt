package com.ritesh.instaclone.data.utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri, folderName: String, callback: (String?) -> Unit) {
    var imageurl: String? = null
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageurl = it.toString()
                callback(imageurl)
            }
        }
}

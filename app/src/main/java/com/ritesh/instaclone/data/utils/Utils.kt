package com.ritesh.instaclone.data.utils

import android.app.ProgressDialog
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

fun uploadVideo(
    uri: Uri,
    folderName: String,
    progressDialog: ProgressDialog,
    callback: (String?) -> Unit
) {
    var videourl: String? = null
    progressDialog.setTitle("Uploading. . .")
    progressDialog.show()
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                videourl = it.toString()
                progressDialog.dismiss()
                callback(videourl)
            }
        }
        .addOnProgressListener {
            val uploadedValue: Long = (it.bytesTransferred / it.totalByteCount) * 100
            progressDialog.setMessage("uploaded $uploadedValue %")
        }
}
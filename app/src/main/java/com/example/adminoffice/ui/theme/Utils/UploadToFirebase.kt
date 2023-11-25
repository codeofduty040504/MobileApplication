package com.example.adminoffice.ui.theme.Utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddServiceCategory
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

// UploadImage method
fun uploadImageToFireBase(context : Context, filePath: Uri): String {
    // get the Firebase  storage reference
    var storage = FirebaseStorage.getInstance();
    var storageReference = storage.getReference();
    var imageURL = ""
    var message = ""
    if (filePath != null) {
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()

        // Defining the child of storageReference
        val ref: StorageReference = storageReference
            .child(
                "images/"
                        + UUID.randomUUID().toString()
            )

        ref.putFile(filePath)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast
                    .makeText(
                        context,
                        "Image Uploaded!!",
                        Toast.LENGTH_SHORT
                    )
                    .show()
                val hap = ref.downloadUrl
                Log.d("HAPP",hap.toString())
                ref.downloadUrl
                    .addOnSuccessListener {
                        Log.d("HHHH",it.toString())
                        imageURL = it.toString()
                    }
                    .addOnFailureListener {
                        Toast
                            .makeText(
                                context,
                                "Image URL cannot be fetched.",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
            }
            .addOnFailureListener { e -> // Error, Image not uploaded
                progressDialog.dismiss()
                Toast
                    .makeText(
                        context,
                        "Failed " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
            .addOnProgressListener { taskSnapshot ->

                val progress = ((100.0
                        * taskSnapshot.bytesTransferred
                        / taskSnapshot.totalByteCount))
                progressDialog.setMessage(
                    "Uploaded "
                            + progress.toInt() + "%"
                )
            }

        return imageURL
    }
    return "fgd"
}
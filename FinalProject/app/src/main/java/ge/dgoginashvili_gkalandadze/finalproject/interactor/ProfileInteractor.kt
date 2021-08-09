package ge.dgoginashvili_gkalandadze.finalproject.interactor

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.dataModel.UserData
import ge.dgoginashvili_gkalandadze.finalproject.presenter.ProfilePresenter
import java.io.ByteArrayOutputStream
import com.google.firebase.storage.FirebaseStorage

import com.google.android.gms.tasks.OnSuccessListener

import com.google.android.gms.tasks.OnFailureListener











class ProfileInteractor(val profilePresenter: ProfilePresenter) {
    private lateinit var firebaseAuth: FirebaseAuth
    private val storage = FirebaseStorage.getInstance()

    fun getUserData() {
        Log.d("RRRR", "MDAAA")
        firebaseAuth = FirebaseAuth.getInstance()
        val loggedUser = firebaseAuth.currentUser
        val userMail = loggedUser?.email
        val userName = userMail?.substring(0, userMail.indexOf('@'))
        val dbase = Firebase.database.getReference("Users")
        dbase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    if (child.child("username").value == userName) {
                        val status = child.child("status").value.toString()
                        Log.d("RRRR", "HMMMM")
                        profilePresenter.loadProfileData(userName,status)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                profilePresenter.dbError(error)
            }
        })

        //Get Avatar SOS LATE IMPROVE
        val storageRef = storage.getReferenceFromUrl("gs://finalprojectdb-9aeb8.appspot.com")
        val imagesRef = storageRef.child("${userName}.jpg")
        val ONE_MEGABYTE = (1024 * 1024 * 5).toLong()
        imagesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(OnSuccessListener<ByteArray?> {
            // Data for "images/island.jpg" is returns, use this as needed
            profilePresenter.loadAvatar(it)
        }).addOnFailureListener(OnFailureListener {
            // Handle any errors
        })
    }

    fun updateProfile(avatar: Bitmap?, newStatus: CharSequence) {
        firebaseAuth = FirebaseAuth.getInstance()
        val loggedUser = firebaseAuth.currentUser
        val userMail = loggedUser?.email
        val userName = userMail?.substring(0, userMail.indexOf('@'))
        val newUser = UserData(userName!!, newStatus.toString())
        val dbase = Firebase.database.getReference("Users")
        dbase.child(newUser.username).setValue(newUser).addOnSuccessListener {
            profilePresenter.updateSuccessful()
        }.addOnFailureListener {
            profilePresenter.updateFailed()
        }

        //Update Avatar
        if(avatar != null) {
            val baos = ByteArrayOutputStream()
            avatar.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            val data: ByteArray = baos.toByteArray()
            val storageRef = storage.getReferenceFromUrl("gs://finalprojectdb-9aeb8.appspot.com")
            val imagesRef = storageRef.child("${userName}.jpg")
            val uploadTask = imagesRef.putBytes(data)
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//            val downloadUrl: Uri = taskSnapshot.getDownloadUrl()
                // Do what you want
            }
        }
    }

}
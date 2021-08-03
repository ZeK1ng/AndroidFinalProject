package ge.dgoginashvili_gkalandadze.finalproject.interactor

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.dataModel.UserData
import ge.dgoginashvili_gkalandadze.finalproject.presenter.ProfilePresenter


class ProfileInteractor(val profilePresenter: ProfilePresenter) {
    private lateinit var firebaseAuth: FirebaseAuth

    fun getUserData() {
       firebaseAuth = FirebaseAuth.getInstance()
        val loggedUser = firebaseAuth.currentUser
        val userMail = loggedUser?.email
        val userName = userMail?.substring(0, userMail.indexOf('@'))
        val dbase = Firebase.database.getReference("Users")
        dbase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    if (child.child("name").value == userName) {
                        val status = child.child("status").value.toString()
                        profilePresenter.loadProfileData(userName,status)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                profilePresenter.dbError(error)
            }
        })

    }

    fun updateProfile(newStatus: CharSequence) {
        firebaseAuth = FirebaseAuth.getInstance()
        val loggedUser = firebaseAuth.currentUser
        val userMail = loggedUser?.email
        val userName = userMail?.substring(0, userMail.indexOf('@'))
        val newUser = UserData(userName!!, newStatus.toString())
        val dbase = Firebase.database.getReference("Users")
        dbase.child(newUser.name).setValue(newUser).addOnSuccessListener {
            profilePresenter.updateSuccessful()
        }.addOnFailureListener {
            profilePresenter.updateFailed()
        }
    }

}
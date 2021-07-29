package ge.dgoginashvili_gkalandadze.finalproject.interactor

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.dataModel.UserData
import ge.dgoginashvili_gkalandadze.finalproject.presenter.RegisterPresenter

class RegisterInteractor(val regPresenter: RegisterPresenter) {
    private lateinit var firebaseAuth: FirebaseAuth

    fun saveUserToDB(newUser: UserData) {
        val dbase = Firebase.database.getReference("Users")
        dbase.child(newUser.name).setValue(newUser).addOnSuccessListener {
            regPresenter.onSuccessfulRegister(newUser)
        }.addOnFailureListener {
            regPresenter.onFailedRegister()
        }
    }

    fun checkUserInDb(name: String, pass: String, workStatus: String) {
        val dbase = Firebase.database.getReference("Users")
        dbase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    if (child.child("name").value == name) {
                        regPresenter.userAlreadyRegistered()
                        return
                    }
                }
                regPresenter.registerUser(name,pass,workStatus)
            }
            override fun onCancelled(error: DatabaseError) {
                regPresenter.onFailedRegister()
            }
        })
    }

    fun authUser(name: String, pass: String) {
        firebaseAuth = Firebase.auth
        firebaseAuth.createUserWithEmailAndPassword(name, pass)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    regPresenter.authSuccess()
                } else {
                    // If sign in fails, display a message to the user.
                    regPresenter.autFailed()
                }
            }
    }
}
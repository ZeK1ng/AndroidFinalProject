package ge.dgoginashvili_gkalandadze.finalproject.interactor

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.dataModel.UserData
import ge.dgoginashvili_gkalandadze.finalproject.presenter.RegisterPresenter
import ge.dgoginashvili_gkalandadze.finalproject.utils.Utils

class RegisterInteractor(val regPresenter: RegisterPresenter) {

    fun saveUserToDB(newUser: UserData) {
        val dbase = Firebase.database.getReference("Users")
        dbase.child(newUser.name).setValue(newUser).addOnSuccessListener {
            regPresenter.onSuccessfulRegister()
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
}
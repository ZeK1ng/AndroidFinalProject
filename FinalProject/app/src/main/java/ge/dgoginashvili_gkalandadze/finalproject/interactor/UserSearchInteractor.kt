package ge.dgoginashvili_gkalandadze.finalproject.interactor

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.presenter.UserSearchPresenter

class UserSearchInteractor(val UserSearchPresenter:UserSearchPresenter) {
    fun loadUsers() {
        val dbase = Firebase.database.getReference("Users")
        dbase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                for (child in snapshot.children) {
//                    if (child.child("username").value == userName) {
//                        val status = child.child("status").value.toString()
//                        Log.d("RRRR", "HMMMM")
//                        profilePresenter.loadProfileData(userName, status)
//                    }
//                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
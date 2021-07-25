package ge.dgoginashvili_gkalandadze.finalproject.interactor

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.presenter.LoginPresenter
import ge.dgoginashvili_gkalandadze.finalproject.utils.StatusCodes
import ge.dgoginashvili_gkalandadze.finalproject.utils.Utils
import kotlin.math.log

class LoginInteractor(val loginPresenter: LoginPresenter) {

    fun checkCredsForUser(name: String, pass: String){
        val dbase = Firebase.database.getReference("Users")
        dbase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    if (child.child("name").value == name) {
                        val userHashedPass = child.child("pass").value
                        Log.d("pass", userHashedPass.toString())
                        if (Utils.checkPassByHash(pass, userHashedPass.toString())) {
                            loginPresenter.onSuccessfullAuth()
                        }else{
                            loginPresenter.onFailedCredentials()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                return
            }
        })
    }
}
package ge.dgoginashvili_gkalandadze.finalproject.interactor

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.presenter.LoginPresenter
import ge.dgoginashvili_gkalandadze.finalproject.utils.UtilsHelper

class LoginInteractor(val loginPresenter: LoginPresenter) {
    private lateinit var firebaseAuth: FirebaseAuth

    //Dont Delete This. TEmpate to search in DB
//    fun checkCredsForUser(name: String, pass: String){
//        val dbase = Firebase.database.getReference("Users")
//        dbase.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (child in snapshot.children) {
//                    if (child.child("name").value == name) {
//                        val userHashedPass = child.child("pass").value
//                        Log.d("pass", userHashedPass.toString())
//                        if (UtilsHelper.checkPassByHash(pass, userHashedPass.toString())) {
//                            loginPresenter.onSuccessfulCredentials()
//                        }else{
//                            loginPresenter.onFailedCredentials()
//                        }
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                return
//            }
//        })
//    }
    fun checkCredsForUser(name:String,pass:String){
        val hashedPass = UtilsHelper.hashPwd(pass)
        firebaseAuth = FirebaseAuth.getInstance()
        val mail = "$name@gmail.com"
        firebaseAuth.signInWithEmailAndPassword(mail, hashedPass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    loginPresenter.onSuccessfulCredentials()
                } else {
                    loginPresenter.onFailedCredentials()
                }
            }
    }
}
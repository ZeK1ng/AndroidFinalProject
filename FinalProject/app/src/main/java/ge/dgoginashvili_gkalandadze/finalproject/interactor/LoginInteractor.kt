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
package ge.dgoginashvili_gkalandadze.finalproject.interactor

import com.google.firebase.auth.FirebaseAuth
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
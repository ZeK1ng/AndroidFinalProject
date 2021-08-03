package ge.dgoginashvili_gkalandadze.finalproject.interactor

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.dataModel.UserData
import ge.dgoginashvili_gkalandadze.finalproject.presenter.RegisterPresenter
import ge.dgoginashvili_gkalandadze.finalproject.utils.UtilsHelper

class RegisterInteractor(val regPresenter: RegisterPresenter) {
    private lateinit var firebaseAuth: FirebaseAuth


    fun registerUser(name: String, pass: String, workStatus: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        val pwd = UtilsHelper.hashPwd(pass)
        val mail = "$name@gmail.com"
        firebaseAuth.createUserWithEmailAndPassword(mail, pwd)
            .addOnCompleteListener() {
                if (it.isSuccessful) {
                    val dbase = Firebase.database.getReference("Users")
                    val newUser = UserData(name, workStatus)
                    dbase.child(newUser.name).setValue(newUser).addOnSuccessListener {
                        regPresenter.onSuccessfulRegister()
                    }.addOnFailureListener {
                        regPresenter.onFailedSave()
                    }
                } else {
                    regPresenter.onAuthFailed()
                }
            }
    }
}


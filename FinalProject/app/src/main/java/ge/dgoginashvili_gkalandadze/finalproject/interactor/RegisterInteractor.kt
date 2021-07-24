package ge.dgoginashvili_gkalandadze.finalproject.interactor

import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.dataModel.UserData
import ge.dgoginashvili_gkalandadze.finalproject.presenter.RegisterPresenter

class RegisterInteractor(val regPresenter: RegisterPresenter) {

    fun saveUserToDB(newUser: UserData) {
        val dbase = Firebase.database.getReference("Users")
        dbase.child(newUser.name.toString()).setValue(newUser).addOnSuccessListener {
            regPresenter.onSuccessfulRegister()
        }.addOnFailureListener {
            regPresenter.onFailedRegister()
        }

    }
}
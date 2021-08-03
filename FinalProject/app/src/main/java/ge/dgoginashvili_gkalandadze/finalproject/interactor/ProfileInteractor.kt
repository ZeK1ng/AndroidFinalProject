package ge.dgoginashvili_gkalandadze.finalproject.interactor

import com.google.firebase.auth.FirebaseAuth
import ge.dgoginashvili_gkalandadze.finalproject.presenter.ProfilePresenter


class ProfileInteractor(val profilePresenter: ProfilePresenter) {
    private lateinit var firebaseAuth: FirebaseAuth

    fun getUserData() {
        firebaseAuth = FirebaseAuth.getInstance()
        val loggedUser = firebaseAuth.currentUser
        val userMail = loggedUser?.email
        val userName = userMail?.substring(0, userMail.indexOf('@'))
        val status = "bla"
        profilePresenter.loadProfileData(userName,status)
    }

}
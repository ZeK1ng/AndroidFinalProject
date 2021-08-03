package ge.dgoginashvili_gkalandadze.finalproject.presenter

import com.google.firebase.database.DatabaseError
import ge.dgoginashvili_gkalandadze.finalproject.activities.ProfilePageActivity
import ge.dgoginashvili_gkalandadze.finalproject.interactor.ProfileInteractor

class ProfilePresenter(var profileActivity: ProfilePageActivity?) {

    private val profileInteractor = ProfileInteractor(this)

    fun loadProfile() {
        profileInteractor.getUserData()
    }

    fun loadProfileData(userName: String?, status: String) {
        profileActivity?.nameView?.text  = userName
        profileActivity?.statusView?.text = status
    }

    fun dbError(error: DatabaseError) {
        profileActivity?.dbError(error)
    }

    fun updateProfile(newStatus: CharSequence) {
        profileInteractor.updateProfile(newStatus)
    }

    fun updateSuccessful() {
        profileInteractor.getUserData()
    }

    fun updateFailed() {
       profileActivity?.updateFailed()
    }

}
package ge.dgoginashvili_gkalandadze.finalproject.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.database.DatabaseError
import ge.dgoginashvili_gkalandadze.finalproject.activities.ProfilePageActivity
import ge.dgoginashvili_gkalandadze.finalproject.interactor.ProfileInteractor

class ProfilePresenter(var profileActivity: ProfilePageActivity?) {

    private val profileInteractor = ProfileInteractor(this)

    fun loadProfile() {
        profileInteractor.getUserData()
    }

    fun loadProfileData(userName: String?, status: String) {
        Log.d("RRRR", "VAH KI DA")
        profileActivity?.nameView?.text  = userName
        profileActivity?.statusView?.text = status
    }

    fun loadAvatar(byteArray: ByteArray) {
        profileActivity?.avatar?.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0,
            byteArray.size))
    }

    fun dbError(error: DatabaseError) {
        profileActivity?.dbError(error)
    }

    fun updateProfile(avatar: Bitmap?, newStatus: CharSequence) {
        profileInteractor.updateProfile(avatar, newStatus)
    }

    fun updateSuccessful() {
        profileInteractor.getUserData()
    }

    fun updateFailed() {
       profileActivity?.updateFailed()
    }

}
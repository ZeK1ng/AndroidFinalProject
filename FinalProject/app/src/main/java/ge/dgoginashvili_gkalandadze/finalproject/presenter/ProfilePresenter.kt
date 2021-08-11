package ge.dgoginashvili_gkalandadze.finalproject.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.firebase.database.DatabaseError
import ge.dgoginashvili_gkalandadze.finalproject.fragment.ProfilePageFragment
import ge.dgoginashvili_gkalandadze.finalproject.interactor.ProfileInteractor

class ProfilePresenter(var profilePageFragment: ProfilePageFragment) {

    private val profileInteractor = ProfileInteractor(this)

    fun loadProfile() {
        profileInteractor.getUserData()
    }

    fun loadProfileData(userName: String?, status: String) {
        profilePageFragment.nameView.text = userName
        profilePageFragment.statusView.text = status
    }

    fun loadAvatar(byteArray: ByteArray) {
        profilePageFragment.avatar.setImageBitmap(
            BitmapFactory.decodeByteArray(
                byteArray, 0,
                byteArray.size
            )
        )
    }

    fun dbError(error: DatabaseError) {
        profilePageFragment.dbError(error)
    }

    fun updateProfile(avatar: Bitmap?, newStatus: CharSequence) {
        profileInteractor.updateProfile(avatar, newStatus)
    }

    fun updateSuccessful() {
        profileInteractor.getUserData()
    }

    fun updateFailed() {
        profilePageFragment.updateFailed()
    }

}
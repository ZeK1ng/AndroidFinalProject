package ge.dgoginashvili_gkalandadze.finalproject.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.firebase.database.DatabaseError
import com.google.firebase.storage.FirebaseStorage
//import ge.dgoginashvili_gkalandadze.finalproject.GlideApp
import ge.dgoginashvili_gkalandadze.finalproject.fragment.ProfilePageFragment
import ge.dgoginashvili_gkalandadze.finalproject.interactor.ProfileInteractor
import ge.dgoginashvili_gkalandadze.finalproject.AppGlide
class ProfilePresenter(var profilePageFragment: ProfilePageFragment) {

    private val profileInteractor = ProfileInteractor(this)

    fun loadProfile() {
        profileInteractor.getUserData()
    }

    fun loadProfileData(userName: String?, status: String) {
        profilePageFragment.nameView.text = userName
        profilePageFragment.statusView.text = status
//        val storage = FirebaseStorage.getInstance()
//        val storageRef = storage.getReferenceFromUrl("gs://finalprojectdb-9aeb8.appspot.com")
//        val imagesRef = storageRef.child("${userName}.jpg")
////        Glide.with(profilePageFragment.requireContext()).load(imagesRef).into(profilePageFragment.avatar)
//        GlideApp.with(profilePageFragment.requireContext() /* context */)
//            .load(imagesRef)
//            .into(profilePageFragment.avatar);
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
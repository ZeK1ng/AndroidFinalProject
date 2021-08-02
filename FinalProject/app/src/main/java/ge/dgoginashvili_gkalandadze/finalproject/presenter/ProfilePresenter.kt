package ge.dgoginashvili_gkalandadze.finalproject.presenter

import ge.dgoginashvili_gkalandadze.finalproject.activities.ProfilePageActivity
import ge.dgoginashvili_gkalandadze.finalproject.interactor.ProfileInteractor

class ProfilePresenter(var profileActivity: ProfilePageActivity?) {

    private val profileInteractor = ProfileInteractor(this)

    fun loadProfile() {
        TODO("Not yet implemented")
    }

}